package com.meetravel.domain.place.service;

import com.meetravel.domain.place.dto.GetTravelPlaceLikeCountListResponse;
import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.place.repository.TravelPlaceRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlaceService {

    private final TravelPlaceRepository travelPlaceRepository;

    @Transactional
    public void review(String placeId) {
        TravelPlaceEntity travelPlace = travelPlaceRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND));

        travelPlace.incrementLikeCount();
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
