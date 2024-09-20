package com.meetravel.domain.place.controller;

import com.meetravel.domain.place.dto.GetTravelPlaceLikeCountListResponse;
import com.meetravel.domain.place.service.TravelPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class TravelPlaceController implements TravelPlaceControllerDoc{

    private final TravelPlaceService travelPlaceService;

    @Override
    @PostMapping("/reviews")
    public void review(@RequestParam String placeId) {
        travelPlaceService.review(placeId);
    }

    @Override
    @DeleteMapping("/reviews/cancel")
    public void cancelReview(@RequestParam String placeId) {
        travelPlaceService.cancelReview(placeId);
    }

    @Override
    @GetMapping
    public ResponseEntity<GetTravelPlaceLikeCountListResponse> getTravelPlaceLikeCount(@RequestParam List<String> placeIds) {
        return ResponseEntity.ok(travelPlaceService.getTravelPlaceLikeCountList(placeIds));
    }

}
