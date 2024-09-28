package com.meetravel.domain.matching_form.dto.request;

import com.meetravel.domain.matching_form.enums.Duration;
import com.meetravel.domain.matching_form.enums.GenderRatio;

import com.meetravel.domain.matching_form.enums.TravelKeyword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class UpdateMatchingFormRequest {

    @NotNull
    private Long matchingFormId;
    private Duration duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private GenderRatio genderRatio;
    private Area area;
    private DetailArea detailArea;
    @Schema(description = "조회했을 때, 체크되어있는 여행 키워드 ID 값(Long 타입)")
    @NotNull(message = "빈 리스트일 수는 있으나, NULL 이어서는 안됩니다.")
    private List<Long> travelKeywordToDelete;
    @Schema(description = "추가될 여행키워드(산,바다 등)")
    @NotNull(message = "빈 리스트일 수는 있으나, NULL 이어서는 안됩니다.")
    private List<@NotNull(message = "NULL 이어서는 안됩니다.") TravelKeyword> travelKeywordToAdd;

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
