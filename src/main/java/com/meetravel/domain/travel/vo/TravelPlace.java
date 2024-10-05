package com.meetravel.domain.travel.vo;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.place.enums.PlaceType;
import com.meetravel.domain.travel.entity.DailyTravelPlaceEntity;
import com.meetravel.domain.travel.entity.ShareTravelPlaceEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class TravelPlace {
    @Schema(description = "장소 고유 번호")
    private final String placeId;
    @Schema(description = "장소 유형")
    private final PlaceType placeType;
    @Schema(description = "장소 제목")
    private final String placeTitle;
    @Schema(description = "장소 주소")
    private final String placeAddress1;
    @Schema(description = "장소 주소 상세")
    private final String placeAddress2;
    @Schema(description = "장소 썸네일")
    private final String placeImageUrl;
    @Getter(AccessLevel.NONE)
    @Schema(description = "장소 선택 여부")
    private final boolean isPicked;

    public boolean getIsPicked() {
        return this.isPicked;
    }

    public TravelPlace(ShareTravelPlaceEntity shareTravelPlaceEntity) {
        TravelPlaceEntity travelPlaceEntity = shareTravelPlaceEntity.getTravelPlace();

        this.placeId = travelPlaceEntity.getPlaceId();
        this.placeType = travelPlaceEntity.getPlaceType();
        this.placeTitle = travelPlaceEntity.getTitle();
        this.placeAddress1 = travelPlaceEntity.getAddress1();
        this.placeAddress2 = travelPlaceEntity.getAddress2();
        this.placeImageUrl = travelPlaceEntity.getImageUrl();
        this.isPicked = false;
    }

    public TravelPlace(DailyTravelPlaceEntity dailyTravelPlaceEntity) {
        TravelPlaceEntity travelPlaceEntity = dailyTravelPlaceEntity.getTravelPlace();

        this.placeId = travelPlaceEntity.getPlaceId();
        this.placeType = travelPlaceEntity.getPlaceType();
        this.placeTitle = travelPlaceEntity.getTitle();
        this.placeAddress1 = travelPlaceEntity.getAddress1();
        this.placeAddress2 = travelPlaceEntity.getAddress2();
        this.placeImageUrl = travelPlaceEntity.getImageUrl();
        this.isPicked = true;
    }
}
