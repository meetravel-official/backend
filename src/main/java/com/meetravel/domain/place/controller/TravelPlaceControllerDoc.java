package com.meetravel.domain.place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "여행 장소(정보) API")
public interface TravelPlaceControllerDoc {

    @Operation(summary = "여행 장소(정보) 평가(하트)", description = "여행 장소(정보)를 평가(하트)합니다.")
    void review(@RequestParam Long travelPlaceId);
    @Operation(summary = "여행 장소(정보) 평가(하트) 취소", description = "여행 장소(정보) 평가(하트)를 취소합니다.")
    void cancelReview(@RequestParam Long travelPlaceId);

}
