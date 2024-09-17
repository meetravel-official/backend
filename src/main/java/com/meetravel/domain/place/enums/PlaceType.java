package com.meetravel.domain.place.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum PlaceType implements BaseEnum {

    /** 여행 계획서에 작성된 장소 유형 */
    TOURIST_SPOT("관광"),
    RESTAURANT("식당"),
    ACCOMMODATION("숙박");

    @JsonValue
    private final String value;

    PlaceType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PlaceType fromValueToEnum(String value) {
        for (PlaceType type : PlaceType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }

}
