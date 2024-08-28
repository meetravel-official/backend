package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Cost implements BaseEnum {

    COST_1_TO_5("1~5만원"),
    COST_6_TO_10("6~10만원"),
    COST_11_TO_15("11~15만원"),
    COST_16_TO_20("16~20만원"),
    COST_21_PLUS("21만원 이상");

    @JsonValue
    private final String value;

    @JsonCreator
    public static Cost fromValueToEnum(String value) {
        for (Cost cost : Cost.values()) {
            if (cost.getValue().equals(value)) {
                return cost;
            }
        }
        return null;
    }

}
