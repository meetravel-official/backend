package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupSize implements BaseEnum {
    FOUR_PEOPLE("4명"),
    SIX_PEOPLE("6명"),
    EIGHT_PEOPLE("8명");

    @JsonValue
    private final String value;

    @JsonCreator
    public static GroupSize fromValueToEnum(String value) {
        for (GroupSize size : GroupSize.values()) {
            if (size.getValue().equals(value)) {
                return size;
            }
        }
        return null;
    }

}
