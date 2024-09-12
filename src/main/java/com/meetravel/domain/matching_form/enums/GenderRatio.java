package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderRatio implements BaseEnum {
    MALE_FEMALE_1_1("이성끼리"),
    SAME_GENDER("동성끼리");

    @JsonValue
    private final String value;

    @JsonCreator
    public static GenderRatio fromValueToEnum(String value) {
        for (GenderRatio gender : GenderRatio.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        return null;
    }
}
