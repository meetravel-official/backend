package com.meetravel.domain.travel.service;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.travel.dto.GetChatRoomTravelPlanResponse;
import com.meetravel.domain.travel.dto.ModifyDailyTravelPlanRequest;
import com.meetravel.domain.travel.dto.ModifyTravelPlanKeywordRequest;
import com.meetravel.domain.travel.entity.*;
import com.meetravel.domain.travel.repository.DailyPlanRepository;
import com.meetravel.domain.travel.repository.DailyTravelPlaceRepository;
import com.meetravel.domain.travel.repository.TravelPlanKeywordRepository;
import com.meetravel.domain.travel.repository.TravelPlanRepository;
import com.meetravel.domain.travel.vo.DailyPlan;
import com.meetravel.domain.travel.vo.TravelPlace;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final TravelPlanKeywordRepository travelPlanKeywordRepository;
    private final DailyTravelPlaceRepository dailyTravelPlaceRepository;
    private final DailyPlanRepository dailyPlanRepository;

    @Transactional(readOnly = true)
    public GetChatRoomTravelPlanResponse getTravelPlanWithChatRoom(
            String userId,
            Long chatRoomId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> userEntity.getUserId().equals(it.getUser().getUserId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_JOINED));

        TravelPlanEntity travelPlanEntity = travelPlanRepository.findByChatRoom(chatRoomEntity)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_TRAVEL_PLAN));

        List<ShareTravelPlaceEntity> shareTravelPlaceEntities = travelPlanEntity.getShareTravelPlaces();

        return new GetChatRoomTravelPlanResponse(
                chatRoomEntity.getId(),
                travelPlanEntity.getTravelPlanKeywords()
                        .stream()
                        .map(TravelPlanKeywordEntity::getKeyword)
                        .toList(),
                travelPlanEntity.getDailyPlans()
                        .stream()
                        .map(dailyPlanEntity -> {
                            Map<String, DailyTravelPlaceEntity> dailyTravelPlaceEntityToPlaceId = dailyPlanEntity.getDailyTravelPlaces()
                                    .stream()
                                    .collect(Collectors.toMap(
                                            it -> it.getTravelPlace().getPlaceId(),
                                            it -> it
                                    ));

                            List<TravelPlace> travelPlaces = shareTravelPlaceEntities
                                    .stream()
                                    .map(it -> {
                                        DailyTravelPlaceEntity dailyTravelPlaceEntity = dailyTravelPlaceEntityToPlaceId.get(it.getTravelPlace().getPlaceId());

                                        if (dailyTravelPlaceEntity != null) {
                                            return new TravelPlace(dailyTravelPlaceEntity);
                                        }

                                        return new TravelPlace(it);
                                    })
                                    .toList();

                            return new DailyPlan(dailyPlanEntity, travelPlaces);
                        })
                        .toList()
        );
    }

    @Transactional
    public void modifyTravelPlanKeywords(
            String userId,
            Long chatRoomId,
            ModifyTravelPlanKeywordRequest request
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> userEntity.getUserId().equals(it.getUser().getUserId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_JOINED));

        TravelPlanEntity travelPlanEntity = travelPlanRepository.findByChatRoom(chatRoomEntity)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_TRAVEL_PLAN));

        List<TravelPlanKeywordEntity> deleteTravelPlanKeywordEntities = travelPlanEntity.getTravelPlanKeywords()
                .stream()
                .filter(it -> !request.getTravelKeywords().contains(it.getKeyword()))
                .toList();

        List<TravelPlanKeywordEntity> saveTravelPlanKeywordEntities = new ArrayList<>();
        request.getTravelKeywords()
                .forEach(travelKeyword -> {
                    TravelPlanKeywordEntity travelPlanKeywordEntity = travelPlanEntity.getTravelPlanKeywords()
                            .stream()
                            .filter(it -> travelKeyword.equals(it.getKeyword()))
                            .findFirst()
                            .orElse(null);

                    if (travelPlanKeywordEntity == null) {
                        saveTravelPlanKeywordEntities.add(
                                new TravelPlanKeywordEntity(
                                        travelPlanEntity,
                                        travelKeyword
                                )
                        );
                    }
                });

        travelPlanEntity.removeTravelPlanKeywords(deleteTravelPlanKeywordEntities);
        travelPlanRepository.save(travelPlanEntity);
        travelPlanKeywordRepository.saveAll(saveTravelPlanKeywordEntities);
    }

    @Transactional
    public void modifyDailyTravelPlans(
            String userId,
            Long chatRoomId,
            ModifyDailyTravelPlanRequest request
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        chatRoomEntity.getUserChatRooms()
                .stream()
                .filter(it -> userEntity.getUserId().equals(it.getUser().getUserId()) && it.getLeaveAt() == null)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_ROOM_NOT_JOINED));

        TravelPlanEntity travelPlanEntity = travelPlanRepository.findByChatRoom(chatRoomEntity)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_TRAVEL_PLAN));

        List<LocalDate> travelPlanDates = travelPlanEntity.getDailyPlans()
                .stream()
                .map(DailyPlanEntity::getPlanDate)
                .toList();

        request.getDailyPlans()
                .forEach(it -> {
                    if (!travelPlanDates.contains(it.getPlanDate())) {
                        throw new BadRequestException(ErrorCode.TRAVEL_PLAN_DATE_NOT_VALID);
                    }
                });

        Map<String, TravelPlaceEntity> placeIdToTravelPlaceEntities = travelPlanEntity.getShareTravelPlaces()
                .stream()
                .map(ShareTravelPlaceEntity::getTravelPlace)
                .collect(Collectors.toMap(TravelPlaceEntity::getPlaceId, Function.identity()));

        request.getDailyPlans()
                .forEach(modifyDailyPlanRequest -> {
                    modifyDailyPlanRequest.getPickedTravelPlaceIds().forEach(it -> {
                        if (!placeIdToTravelPlaceEntities.containsKey(it)) {
                            throw new BadRequestException(ErrorCode.NOT_SHARED_TRAVEL_PLACE_PICK);
                        }
                    });
                });

        request.getDailyPlans()
                .forEach(modifyDailyPlanRequest -> {
                    travelPlanEntity.getShareTravelPlaces()
                            .stream()
                            .map(ShareTravelPlaceEntity::getTravelPlace)
                            .filter(it -> modifyDailyPlanRequest.getPickedTravelPlaceIds().contains(it.getPlaceId()))
                            .collect(Collectors.groupingBy(TravelPlaceEntity::getPlaceType))
                            .forEach((key, value) -> {
                                if (value.size() > 2) {
                                    throw new BadRequestException(ErrorCode.PLACE_TYPE_PICK_LIMIT);
                                }
                            });
                });

        Map<LocalDate, DailyPlanEntity> planDateToDailyPlanEntities = travelPlanEntity.getDailyPlans()
                .stream()
                .collect(Collectors.toMap(DailyPlanEntity::getPlanDate, Function.identity()));

        List<DailyPlanEntity> dailyPlanEntities = request.getDailyPlans()
                .stream()
                .map(it -> {
                    DailyPlanEntity dailyPlanEntity = planDateToDailyPlanEntities.get(it.getPlanDate());

                    dailyPlanEntity.setMeetPlace(it.getMeetPlace());
                    dailyPlanEntity.setMeetTime(it.getMeetTime());

                    dailyPlanEntity.removeAllTravelPlaces();
                    List<DailyTravelPlaceEntity> savedDailyTravelPlaceEntities = it.getPickedTravelPlaceIds()
                            .stream()
                            .map(placeId -> {
                                TravelPlaceEntity travelPlaceEntity = placeIdToTravelPlaceEntities.get(placeId);
                                return dailyTravelPlaceRepository.save(new DailyTravelPlaceEntity(
                                        dailyPlanEntity,
                                        travelPlaceEntity
                                ));
                            })
                            .toList();
                    dailyPlanEntity.addTravelPlaces(savedDailyTravelPlaceEntities);

                    return dailyPlanEntity;
                })
                .toList();
        dailyPlanRepository.saveAll(dailyPlanEntities);
    }
}
