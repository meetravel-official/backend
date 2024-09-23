package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GroupSize implements BaseEnum {
    FOUR_PEOPLE("4명", 4),
    SIX_PEOPLE("6명", 6),
    EIGHT_PEOPLE("8명", 8);

    @JsonValue
    private final String value;
    private final int number;

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
