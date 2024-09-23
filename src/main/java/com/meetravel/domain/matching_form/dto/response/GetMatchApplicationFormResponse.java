package com.meetravel.domain.matching_form.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class GetMatchApplicationFormResponse {
    @Schema(
            description = "매칭 신청서 고유 번호",
            nullable = true
    )
    private final Long matchingFormId;

    public GetMatchApplicationFormResponse() {
        this.matchingFormId = null;
    }

    public GetMatchApplicationFormResponse(Long matchingFormId) {
        this.matchingFormId = matchingFormId;
    }
}
