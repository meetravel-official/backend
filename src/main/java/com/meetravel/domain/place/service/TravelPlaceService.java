package com.meetravel.domain.place.service;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.place.dto.GetTravelPlaceLikeCountListResponse;
import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.place.enums.PlaceType;
import com.meetravel.domain.place.repository.TravelPlaceRepository;
import com.meetravel.domain.tour_api.TourApiAreaFeignClient;
import com.meetravel.domain.tour_api.TourApiDetailCommonResponse;
import com.meetravel.domain.travel.entity.ShareTravelPlaceEntity;
import com.meetravel.domain.travel.repository.ShareTravelPlaceRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlaceService {
    @Value("${tour_api.service_key}")
    private String serviceKey;

    private final TourApiAreaFeignClient tourApiAreaFeignClient;
    private final TravelPlaceRepository travelPlaceRepository;
    private final UserRepository userRepository;
    private final ShareTravelPlaceRepository shareTravelPlaceRepository;

    @Transactional
    public void review(String placeId) {
        TravelPlaceEntity travelPlace = travelPlaceRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND));

        travelPlace.incrementLikeCount();
    }

    @Transactional
    public void shareTravelPlace(
            String userId,
            String placeId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        UserChatRoomEntity userChatRoomEntity = userEntity.getUserChatRooms()
                .stream()
                .filter(it -> it.getLeaveAt() == null)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_JOINED_CHAT_ROOM));

        ChatRoomEntity chatRoomEntity = userChatRoomEntity.getChatRoom();

        ShareTravelPlaceEntity oldShareTravelPlaceEntity = chatRoomEntity.getTravelPlan().getShareTravelPlaces()
                .stream()
                .filter(it -> it.getTravelPlace().getPlaceId().equals(placeId))
                .findFirst()
                .orElse(null);
        if (oldShareTravelPlaceEntity != null) return;

        TravelPlaceEntity travelPlaceEntity = travelPlaceRepository.findById(placeId).orElse(null);
        if (travelPlaceEntity == null) {
            TourApiDetailCommonResponse response = tourApiAreaFeignClient.getDetailCommon(
                    "ETC",
                    "미트래블",
                    "json",
                    placeId,
                    null,
                    "Y",
                    "Y",
                    null,
                    null,
                    "Y",
                    null,
                    null,
                    1,
                    1,
                    serviceKey
            );

            travelPlaceEntity = response.getResponse()
                    .getBody()
                    .getItems()
                    .getItem()
                    .stream()
                    .findFirst()
                    .map(it -> travelPlaceRepository.save(
                            TravelPlaceEntity.builder()
                                    .placeId(it.getContentid())
                                    .placeType(PlaceType.generateFromContentTypeId(it.getContenttypeid()))
                                    .title(it.getTitle())
                                    .address1(it.getAddr1())
                                    .address2(it.getAddr2())
                                    .imageUrl(it.getFirstimage())
                                    .build()
                    ))
                    .orElse(null);
        }

        if (travelPlaceEntity == null) {
            throw new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND);
        }

        ShareTravelPlaceEntity shareTravelPlaceEntity = new ShareTravelPlaceEntity(
                chatRoomEntity.getTravelPlan(),
                travelPlaceEntity
        );
        shareTravelPlaceRepository.save(shareTravelPlaceEntity);

        // TODO 공유하기 메세지 발송 로직 추가
    }

    @Transactional
    public void cancelReview(String placeId) {
        TravelPlaceEntity travelPlace = travelPlaceRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND));

        travelPlace.decrementLikeCount();
    }

    @Transactional(readOnly = true)
    public GetTravelPlaceLikeCountListResponse getTravelPlaceLikeCountList(List<String> placeIds) {
        List<TravelPlaceEntity> travelPlaceEntityList = travelPlaceRepository.findByPlaceIdIn(placeIds);

        List<GetTravelPlaceLikeCountListResponse.TravelPlace> travelPlaceCountList = travelPlaceEntityList.stream()
                .map(travelPlaceEntity -> GetTravelPlaceLikeCountListResponse.TravelPlace.builder()
                        .id(travelPlaceEntity.getPlaceId())
                        .likeCount(travelPlaceEntity.getLikeCount())
                        .build())
                .toList();

        return GetTravelPlaceLikeCountListResponse.builder().travelPlaceCountList(travelPlaceCountList).build();

    }



}
