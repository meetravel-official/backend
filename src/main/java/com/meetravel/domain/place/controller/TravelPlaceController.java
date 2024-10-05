package com.meetravel.domain.place.controller;

import com.meetravel.domain.place.dto.GetTravelPlaceLikeCountListResponse;
import com.meetravel.domain.place.service.TravelPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class TravelPlaceController implements TravelPlaceControllerDoc {

    private final TravelPlaceService travelPlaceService;

    @Override
    @PostMapping("/reviews")
    public void review(@RequestParam String placeId) {
        travelPlaceService.review(placeId);
    }

    @PostMapping("/share/{placeId}")
    public ResponseEntity<Object> shareTravelPlace(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String placeId
    ) {
        travelPlaceService.shareTravelPlace(
                userDetails.getUsername(),
                placeId
        );

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/reviews/cancel")
    public void cancelReview(@RequestParam String placeId) {
        travelPlaceService.cancelReview(placeId);
    }

    @Override
    @GetMapping("/reviews/count")
    public ResponseEntity<GetTravelPlaceLikeCountListResponse> getTravelPlaceLikeCount(@RequestParam List<String> placeIds) {
        return ResponseEntity.ok(travelPlaceService.getTravelPlaceLikeCountList(placeIds));
    }

}
