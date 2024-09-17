package com.meetravel.domain.chatroom.vo;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.enums.Duration;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TravelPlanDate {
    @Schema(description = "여행 기간 유형")
    private final Duration duration;
    @Schema(description = "여행 시작 일자")
    private final LocalDate startDate;
    @Schema(description = "여행 종료 일자")
    private final LocalDate endDate;

    public TravelPlanDate(MatchingFormEntity matchingFormEntity) {
        this.duration = matchingFormEntity.getDuration();
        this.startDate = matchingFormEntity.getStartDate();
        this.endDate = matchingFormEntity.getEndDate();
    }
}
