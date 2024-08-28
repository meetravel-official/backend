package com.meetravel.domain.auth.service;

import com.meetravel.domain.auth.dto.response.KakaoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(
        name = "kakao-auth-api",
        url = "${oauth.kakao.auth-url}"
)
public interface KakaoAuthFeignClient {

    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoToken getKakaoToken(@RequestBody MultiValueMap<String, String> body);

    @PostMapping(value = "/oauth/tokeninfo", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    KakaoToken.IdToken getIdTokenInfo(@RequestParam("id_token") String idToken);
}
