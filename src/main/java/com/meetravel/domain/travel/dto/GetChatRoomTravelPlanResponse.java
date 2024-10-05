package com.meetravel.domain.travel.dto;

import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.domain.travel.vo.DailyPlan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class GetChatRoomTravelPlanResponse {
    @Schema(description = "채팅방 고유 번호")
    private final Long chatRoomId;
    @Schema(description = "여행 스타일 키워드")
    private final List<TravelKeyword> travelKeywords;
    @Schema(description = "일별 여행 계획")
    private final List<DailyPlan> dailyPlans;

    public GetChatRoomTravelPlanResponse(
        Long chatRoomId,
        List<TravelKeyword> travelKeywords,
        List<DailyPlan> dailyPlans
    ) {
        this.chatRoomId = chatRoomId;
        this.travelKeywords = travelKeywords;
        this.dailyPlans = dailyPlans;
    }
}
