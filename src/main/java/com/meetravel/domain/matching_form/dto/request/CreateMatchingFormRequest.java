package com.meetravel.domain.matching_form.dto.request;

import com.meetravel.domain.matching_form.enums.Cost;
import com.meetravel.domain.matching_form.enums.Duration;
import com.meetravel.domain.matching_form.enums.GenderRatio;
import com.meetravel.domain.matching_form.enums.GroupSize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

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
    }
}
