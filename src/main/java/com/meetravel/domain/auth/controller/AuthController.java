package com.meetravel.domain.auth.controller;

import com.meetravel.domain.auth.dto.response.LoginResponse;
import com.meetravel.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    @Override
    @PostMapping("/kakao/login")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String authorizationCode, @RequestParam String redirectUrl, HttpServletResponse response) {
        log.info("kakao login");
        return ResponseEntity.ok(authService.kakaoLogin(authorizationCode, redirectUrl, response));
    }
}
