package com.meetravel.domain.travel.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

import java.util.List;

@Getter
public class ModifyTravelPlanKeywordRequest {
    @Parameter(description = "여행 스타일 키워드")
    private final List<TravelKeyword> travelKeywords;

    @JsonCreator
    public ModifyTravelPlanKeywordRequest(List<TravelKeyword> travelKeywords) {
        if (travelKeywords.isEmpty() || travelKeywords.size() > 3) {
            throw new BadRequestException(ErrorCode.TRAVEL_KEYWORD_LIMIT);
        }

        this.travelKeywords = travelKeywords;
    }
}
