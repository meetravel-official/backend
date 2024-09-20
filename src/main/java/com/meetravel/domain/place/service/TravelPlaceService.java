package com.meetravel.domain.place.service;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.place.repository.TravelPlaceRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TravelPlaceService {

    private final TravelPlaceRepository travelPlaceRepository;

    @Transactional
    public void review(Long travelPlaceId) {
        TravelPlaceEntity travelPlace = travelPlaceRepository.findById(travelPlaceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND));

        travelPlace.incrementLikeCount();
    }

    @Transactional
    public void cancelReview(Long travelPlaceId) {
        TravelPlaceEntity travelPlace = travelPlaceRepository.findById(travelPlaceId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TRAVEL_PLACE_NOT_FOUND));

        travelPlace.decrementLikeCount();
    }



}
