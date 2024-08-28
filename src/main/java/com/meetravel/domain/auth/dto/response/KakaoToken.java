package com.meetravel.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoToken {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    private Long refreshTokenExpiresIn;

    @JsonProperty("scope")
    private String scope;

    @Getter
    public static class IdToken {
        private String iss;
        private String aud;
        private String sub; // 카카오 고유 회원 번호
        private Integer iat;
        private Integer exp;
        private Integer auth_time;
        private String nonce;
        private String nickname;
        private String picture;
        private String email;
    }
}