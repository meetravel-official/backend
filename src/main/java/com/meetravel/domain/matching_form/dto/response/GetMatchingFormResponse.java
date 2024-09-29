package com.meetravel.domain.matching_form.dto.response;

import com.meetravel.domain.matching_form.enums.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class GetMatchingFormResponse {

    private Long matchingFormId;
    private Duration duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private GroupSize groupSize;
    private GenderRatio genderRatio;
    private Cost cost;
    private Area area;
    private DetailArea detailArea;
    private List<TravelKeyword> travelKeywordList;

    @Getter
    @Builder
    public static class Area {
        private String code;
        private String name;
    }

    @Getter
    @Builder
    public static class DetailArea {
        private String detailCode;
        private String detailName;
    }}
