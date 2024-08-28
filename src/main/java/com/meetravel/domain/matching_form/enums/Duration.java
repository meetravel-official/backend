package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Duration implements BaseEnum {
    DAY_TRIP("당일치기"),
    ONE_NIGHT("1박2일"),
    TWO_NIGHTS("2박3일");

    @JsonValue
    private final String value;

    @JsonCreator
    public static Duration fromValueToEnum(String value) {
        for (Duration duration : Duration.values()) {
            if (duration.getValue().equals(value)) {
                return duration;
            }
        }
        return null;
    }
}
