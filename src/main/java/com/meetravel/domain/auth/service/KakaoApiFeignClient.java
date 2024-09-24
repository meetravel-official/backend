package com.meetravel.domain.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(
        name = "kakao-api",
        url = "https://kapi.kakao.com"
)
public interface KakaoApiFeignClient {

    @PostMapping(value = "/v1/user/unlink", consumes = "application/x-www-form-urlencoded;charset=utf-8")
    void unlinkUser(@RequestHeader("Authorization") String authorization,
                    @RequestParam("target_id_type") String targetIdType,
                    @RequestParam("target_id") Long targetId);
}
