package com.meetravel.domain.matching_form.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.meetravel.global.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum TravelKeyword implements BaseEnum {
    MOUNTAIN("산"),
    SEA("바다"),
    CITY("도시"),
    SUBURB("근교"),
    CAMPING("캠핑"),
    HIKING("걷기여행"),
    NIGHT_VIEW("야경"),
    TRADITIONAL_CULTURE("전통문화"),
    LIFESTYLE_TOURISM("생활관광"),
    LEISURE_EXPERIENCE("레저체험"),
    RELAXATION("휴양"),
    HEALING("힐링");

    @JsonValue
    private final String value;

    TravelKeyword(String value) {
        this.value = value;
    }

    @JsonCreator
    public static TravelKeyword fromValueToEnum(String value) {
        for (TravelKeyword keyword : TravelKeyword.values()) {
            if (keyword.getValue().equals(value)) {
                return keyword;
            }
        }
        throw new IllegalArgumentException("잘못된 여행키워드입니다.");
    }
}
