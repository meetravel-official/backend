package com.meetravel.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Review {
    CONSIDERATE("잘 배려해줘요"),
    CHARMING("매력있어요"),
    WITTY("재치있어요"),
    TALKATIVE("대화가 잘 통해요"),
    KIND("친절해요"),
    POSITIVE("긍정적이에요"),
    CUTE("귀여워요"),
    PASSIONATE("열정적이에요"),
    EMPATHETIC("잘 공감해줘요");

    @JsonValue
    private final String desc;

    @JsonCreator
    public static Review fromValueToEnum(String desc) {
        for (Review review : Review.values()) {
            if (review.getDesc().equals(desc)) {
                return review;
            }
        }
        return null;
    }
}
