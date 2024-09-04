package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum Gender implements BaseEnum {
    MALE("남성"),
    FEMALE("여성");

    @JsonValue
    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Gender fromValueToEnum(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equals(value)) {
                return gender;
            }
        }
        return null;
    }
}