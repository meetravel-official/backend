package com.meetravel.domain.place.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetTravelPlaceLikeCountListResponse {

    List<TravelPlace> travelPlaceCountList;

    @Getter
    @Builder
    public static class TravelPlace {
        @Schema(description = "여행 장소(정보) ID, 관광공사 API의 contentId")
        private String id;
        @Schema(description = "여행 장소 하트 수")
        private long likeCount;

    }
}
