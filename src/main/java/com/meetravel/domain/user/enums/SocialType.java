package com.meetravel.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    KAKAO("@kakao"), BOT("@bot");
    private final String socialSuffix;

}
