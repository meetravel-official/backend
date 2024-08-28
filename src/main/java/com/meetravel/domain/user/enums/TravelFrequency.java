package com.meetravel.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TravelFrequency implements BaseEnum {
    NONE("안가요!"),
    ONE_TO_THREE("1~3"),
    FOUR_TO_SIX("4~6"),
    SEVEN_OR_MORE("7번 이상");

    @JsonValue
    private final String value;

    @JsonCreator
    public static TravelFrequency fromValueToEnum(String value) {
        for (TravelFrequency frequency : TravelFrequency.values()) {
            if (frequency.getValue().equals(value)) {
                return frequency;
            }
        }
        return null;
    }
}
