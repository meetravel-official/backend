package com.meetravel.domain.place.controller;

import com.meetravel.domain.place.service.TravelPlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class TravelPlaceController implements TravelPlaceControllerDoc{

    private final TravelPlaceService travelPlaceService;

    @Override
    @PostMapping("/review")
    public void review(@RequestParam Long travelPlaceId) {
        travelPlaceService.review(travelPlaceId);
    }

    @Override
    @DeleteMapping("/review/cancel")
    public void cancelReview(@RequestParam Long travelPlaceId) {
        travelPlaceService.cancelReview(travelPlaceId);
    }

}
