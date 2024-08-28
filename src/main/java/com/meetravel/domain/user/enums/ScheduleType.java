package com.meetravel.domain.user.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScheduleType implements BaseEnum {

    TIGHT("빠듯하게"),
    RELAXED("여유롭게");

    @JsonValue
    private final String value; // 설명

    @JsonCreator
    public static ScheduleType fromValueToEnum(String value) {
        for (ScheduleType type : ScheduleType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}

