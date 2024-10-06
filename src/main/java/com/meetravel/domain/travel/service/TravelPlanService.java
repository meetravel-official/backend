package com.meetravel.domain.travel.service;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.travel.dto.GetChatRoomTravelPlanResponse;
import com.meetravel.domain.travel.dto.ModifyTravelPlanKeywordRequest;
import com.meetravel.domain.travel.entity.DailyTravelPlaceEntity;
import com.meetravel.domain.travel.entity.ShareTravelPlaceEntity;
import com.meetravel.domain.travel.entity.TravelPlanEntity;
import com.meetravel.domain.travel.entity.TravelPlanKeywordEntity;
import com.meetravel.domain.travel.repository.TravelPlanKeywordRepository;
import com.meetravel.domain.travel.repository.TravelPlanRepository;
import com.meetravel.domain.travel.vo.DailyPlan;
import com.meetravel.domain.travel.vo.TravelPlace;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final TravelPlanRepository travelPlanRepository;
    private final TravelPlanKeywordRepository travelPlanKeywordRepository;

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
}
