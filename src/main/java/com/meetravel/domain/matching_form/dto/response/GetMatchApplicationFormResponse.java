package com.meetravel.domain.matching_form.dto.response;

import lombok.Getter;

@Getter
public class GetMatchApplicationFormResponse {
    private final Long matchingFormId;

    public GetMatchApplicationFormResponse() {
        this.matchingFormId = null;
    }

    public GetMatchApplicationFormResponse(Long matchingFormId) {
        this.matchingFormId = matchingFormId;
    }
}
