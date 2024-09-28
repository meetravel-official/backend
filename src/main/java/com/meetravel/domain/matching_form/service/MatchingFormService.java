package com.meetravel.domain.matching_form.service;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.matching_form.dto.request.CreateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.request.UpdateMatchingFormRequest;
import com.meetravel.domain.matching_form.dto.response.GetAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetDetailAreaResponse;
import com.meetravel.domain.matching_form.dto.response.GetMatchApplicationFormResponse;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.domain.matching_form.repository.MatchingFormRepository;
import com.meetravel.domain.matching_form.repository.TravelKeywordRepository;
import com.meetravel.domain.tour_api.TourApiAreaFeignClient;
import com.meetravel.domain.tour_api.TourApiAreaResponse;
import com.meetravel.domain.tour_api.TourApiDetailAreaResponse;

import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.*;


@Service
@RequiredArgsConstructor
public class MatchingFormService {

    @Value("${tour_api.service_key}")
    private String serviceKey;

    private final UserRepository userRepository;
    private final MatchingFormRepository matchingFormRepository;
    private final TravelKeywordRepository travelKeywordRepository;
    private final TourApiAreaFeignClient tourApiAreaFeginClient;

    /**
     * 매칭 신청서 작성
     *
     * @param request
     */
    @Transactional
    public void createMatchingForm(String userId, CreateMatchingFormRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        MatchingFormEntity matchingForm = MatchingFormEntity.builder()
                .user(user)
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .genderRatio(request.getGenderRatio())
                .areaCode(request.getArea().getCode())
                .areaName(request.getArea().getName())
                .detailAreaCode(request.getDetailArea().getDetailCode())
                .detailAreaName(request.getDetailArea().getDetailName())
                .build();

        // 매칭 신청서 저장
        matchingFormRepository.save(matchingForm);
        
        // 매칭 신청서의 키워드 저장
        for (TravelKeyword keyword : request.getTravelKeywordList()) {
            this.addTravelKeyword(matchingForm, keyword);
        }

    }

    /**
     * 매칭신청서에 매핑되는 여행키워드 저장
     * @param matchingForm
     * @param keyword
     */
    private void addTravelKeyword(MatchingFormEntity matchingForm, TravelKeyword keyword) {
        if (!travelKeywordRepository.existsByMatchingFormAndKeyword(matchingForm, keyword)) {
            TravelKeywordEntity travelKeyword = TravelKeywordEntity.builder()
                    .matchingForm(matchingForm)
                    .keyword(keyword)
                    .build();

            matchingForm.addTravelKeyword(travelKeyword);
            travelKeywordRepository.save(travelKeyword);
        }
    }


    @Transactional
    public void updateMatchingForm(UpdateMatchingFormRequest request) {
        MatchingFormEntity matchingForm = matchingFormRepository
                .findById(request.getMatchingFormId()).orElseThrow(() -> new NotFoundException(ErrorCode.MATCHING_FORM_NOT_FOUND));

        /** 매칭 신청서 항목 수정(키워드 제외) */
        matchingForm.updateMatchingForm(request);


        /** 삭제할 키워드 */
        List<Long> travelKeywordToDelete = request.getTravelKeywordToDelete();
        System.out.println(travelKeywordToDelete);
        System.out.println(request.getTravelKeywordToAdd());
        // 현재 있는 키워드 중에서 삭제할 키워드 목록 생성
        List<Long> keywordsToDelete = matchingForm.getTravelKeywordList().stream()
                .filter(travelKeyword -> travelKeywordToDelete.contains(travelKeyword.getId()))
                .map(TravelKeywordEntity::getId)
                .toList();

        travelKeywordRepository.deleteAllByTravelKeywordId(keywordsToDelete);

        /** 추가할 키워드 */
        for (TravelKeyword keyword : request.getTravelKeywordToAdd()) {
            this.addTravelKeyword(matchingForm, keyword);
        }

    }

    public GetAreaResponse getArea() {
        int numOfRows = 17; // 충분한 수 넣어줌
        int pageNo = 1;
        String mobileOS = "ETC";
        String mobileApp = "미트래블";
        String _type = "json";

        TourApiAreaResponse tourApiAreaResponse = tourApiAreaFeginClient.getArea(numOfRows, pageNo, mobileOS, mobileApp, _type, serviceKey);

        List<GetAreaResponse.Area> areaList = tourApiAreaResponse.getResponse().getBody().getItems().getItemList().stream()
                .map(item -> GetAreaResponse.Area.builder()
                        .code(item.getCode())
                        .name(item.getName())
                        .build())
                .toList();

        return GetAreaResponse.builder().areaList(areaList).build();
    }

    public GetDetailAreaResponse getDetailArea(String code) {
        int numOfRows = 17; // 충분한 수 넣어줌
        int pageNo = 1;
        String mobileOS = "ETC";
        String mobileApp = "미트래블";
        String _type = "json";
        TourApiDetailAreaResponse tourApiDetailAreaResponse = tourApiAreaFeginClient.getDetailArea(numOfRows, pageNo, mobileOS, mobileApp, code, _type, serviceKey);

        List<GetDetailAreaResponse.DetailArea> detailAreaList = tourApiDetailAreaResponse.getResponse().getBody().getItems().getItemList().stream()
                .map(item -> GetDetailAreaResponse.DetailArea.builder()
                        .detailCode(item.getCode())
                        .detailName(item.getName())
                        .build())
                .toList();

        return GetDetailAreaResponse.builder().detailAreaList(detailAreaList).build();

    }

    @Transactional(readOnly = true)
    public GetMatchApplicationFormResponse getMatchedApplicationForm(
            String userId,
            Long matchingFormId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        MatchingFormEntity myMatchingFormEntity = matchingFormRepository.findById(matchingFormId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.MATCHING_FORM_NOT_FOUND));

        if (myMatchingFormEntity.getChatRoom() != null) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_ROOM_WITH_MATCHING_FORM);
        }

        List<Long> myMatchingFormIds = userEntity.getMatchingFormEntities()
                .stream()
                .map(MatchingFormEntity::getId)
                .toList();

        List<MatchingFormEntity> matchingFormEntities;
        if ("all".equals(myMatchingFormEntity.getAreaCode())) {
            matchingFormEntities = matchingFormRepository.findAllByGroupSizeAndGenderRatioAndStartDateBetweenAndIdNotIn(
                    myMatchingFormEntity.getGroupSize(),
                    myMatchingFormEntity.getGenderRatio(),
                    myMatchingFormEntity.getStartDate(),
                    myMatchingFormEntity.getEndDate().plusDays(1),
                    myMatchingFormIds
            );
        } else {
            matchingFormEntities = matchingFormRepository.findAllByAreaCodeAndGroupSizeAndGenderRatioAndStartDateBetweenAndIdNotIn(
                    myMatchingFormEntity.getAreaCode(),
                    myMatchingFormEntity.getGroupSize(),
                    myMatchingFormEntity.getGenderRatio(),
                    myMatchingFormEntity.getStartDate(),
                    myMatchingFormEntity.getEndDate().plusDays(1),
                    myMatchingFormIds
            );
        }

        List<TravelKeyword> myTravelKeywords = myMatchingFormEntity.getTravelKeywordList()
                .stream()
                .map(TravelKeywordEntity::getKeyword)
                .toList();

        return matchingFormEntities.stream()
                .collect(toMap(
                        MatchingFormEntity::getChatRoom,
                        Function.identity(),
                        BinaryOperator.minBy(comparingLong(MatchingFormEntity::getId))
                ))
                .entrySet()
                .stream()
                .filter(entry -> {
                    ChatRoomEntity chatRoomEntity = entry.getKey();
                    MatchingFormEntity matchingFormEntity = entry.getValue();

                    if (chatRoomEntity == null || matchingFormEntity == null) {
                        return false;
                    }

                    List<TravelKeyword> travelKeywords = matchingFormEntity.getTravelKeywordList()
                            .stream()
                            .map(TravelKeywordEntity::getKeyword)
                            .toList();

                    if (travelKeywords.isEmpty() || travelKeywords.stream().noneMatch(myTravelKeywords::contains)) {
                        return false;
                    }

                    long joinedUserCount = chatRoomEntity.getUserChatRooms()
                            .stream()
                            .filter(it -> it.getLeaveAt() == null)
                            .count();

                    long remainingUserCount = matchingFormEntity.getGroupSize().getNumber() - joinedUserCount;
                    return remainingUserCount <= matchingFormEntity.getGroupSize().getNumber();
                })
                .map(entry -> {
                    ChatRoomEntity chatRoomEntity = entry.getKey();
                    MatchingFormEntity matchingFormEntity = entry.getValue();

                    long joinedUserCount = chatRoomEntity.getUserChatRooms()
                            .stream()
                            .filter(it -> it.getLeaveAt() == null)
                            .count();

                    long remainingUserCount = matchingFormEntity.getGroupSize().getNumber() - joinedUserCount;

                    return new AbstractMap.SimpleEntry<>(
                            entry,
                            Triple.of(remainingUserCount, matchingFormEntity.getStartDate(), matchingFormEntity.getId())
                    );
                })
                .sorted(comparing((Map.Entry<Map.Entry<ChatRoomEntity, MatchingFormEntity>, Triple<Long, LocalDate, Long>> entry) -> entry.getValue().getLeft())
                                .thenComparing(entry -> entry.getValue().getMiddle())
                                .thenComparing(entry -> entry.getValue().getRight())
                )
                .map(Map.Entry::getKey)
                .findFirst()
                .map(it -> new GetMatchApplicationFormResponse(it.getValue().getId()))
                .orElse(new GetMatchApplicationFormResponse());
    }
}
