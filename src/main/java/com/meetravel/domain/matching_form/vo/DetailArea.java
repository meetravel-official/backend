package com.meetravel.domain.matching_form.vo;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DetailArea {
    @Schema(description = "지역 소분류 코드")
    private final String areaCode;
    @Schema(description = "지역 소분류 이름")
    private final String areaName;

    public DetailArea(MatchingFormEntity matchingFormEntity) {
        this.areaCode = matchingFormEntity.getDetailAreaCode();
        this.areaName = matchingFormEntity.getDetailAreaName();
    }
}
