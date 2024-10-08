package com.meetravel.domain.matching_form.dto.request;

import com.meetravel.domain.matching_form.enums.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class CreateMatchingFormRequest {

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
    public static class Area {
        private String code;
        private String name;
    }

    @Getter
    public static class DetailArea {
        private String detailCode;
        private String detailName;
    }
}
