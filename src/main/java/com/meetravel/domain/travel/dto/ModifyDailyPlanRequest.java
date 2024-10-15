package com.meetravel.domain.travel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ModifyDailyPlanRequest {
    @Parameter(description = "계획 일자")
    private final LocalDate planDate;
    @Parameter(description = "집합 장소")
    private final String meetPlace;
    @Parameter(description = "만날 시간")
    private final String meetTime;
    @Parameter(description = "선택한 함께 할 장소의 고유 번호 리스트")
    private final List<String> pickedTravelPlaceIds;

    @JsonCreator
    public ModifyDailyPlanRequest(
            LocalDate planDate,
            String meetPlace,
            String meetTime,
            List<String> pickedTravelPlaceIds
    ) {
        this.planDate = planDate;
        this.meetPlace = meetPlace;
        this.meetTime = meetTime;
        this.pickedTravelPlaceIds = pickedTravelPlaceIds;
    }
}
