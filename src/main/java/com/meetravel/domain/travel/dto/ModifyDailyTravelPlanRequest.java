package com.meetravel.domain.travel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifyDailyTravelPlanRequest {
    @Parameter(description = "일별 여행 계획")
    private final List<ModifyDailyPlanRequest> dailyPlans;

    @JsonCreator
    public ModifyDailyTravelPlanRequest(
            List<ModifyDailyPlanRequest> dailyPlans
    ) {
        this.dailyPlans = dailyPlans;
    }
}
