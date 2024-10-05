package com.meetravel.domain.travel.vo;

import com.meetravel.domain.travel.entity.DailyPlanEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class DailyPlan {
    @Schema(description = "계획 일자")
    private final String planDate;
    @Schema(description = "집합 장소")
    private final String meetPlace;
    @Schema(description = "만날 시간")
    private final String meetTime;
    @Schema(description = "함께 할 장소")
    private final List<TravelPlace> travelPlaces;

    public DailyPlan(
        DailyPlanEntity dailyPlanEntity,
        List<TravelPlace> travelPlaces
    ) {
        this.planDate = dailyPlanEntity.getPlanDate().toString();
        this.meetPlace = dailyPlanEntity.getMeetPlace();
        this.meetTime = dailyPlanEntity.getMeetTime();
        this.travelPlaces = travelPlaces;
    }
}
