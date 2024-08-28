package com.meetravel.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlanningType implements BaseEnum {

    PLANNED("계획적으로"),
    IMPROMPTU("즉흥적으로");

    @JsonValue
    private final String value; // 설명

    @JsonCreator
    public static PlanningType fromValueToEnum(String value) {
        for (PlanningType type : PlanningType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
