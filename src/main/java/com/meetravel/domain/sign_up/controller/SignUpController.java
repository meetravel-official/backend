package com.meetravel.domain.sign_up.controller;

import com.meetravel.domain.sign_up.dto.request.SignUpRequest;
import com.meetravel.domain.sign_up.dto.response.GetSignUpInfoList;
import com.meetravel.domain.sign_up.service.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController implements SignUpControllerDoc {

    private final SignUpService signUpService;

    @Override
    @PostMapping("")
    public void signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("회원가입");
        signUpService.signUp(signUpRequest);
    }

    @Override
    @GetMapping("/info")
    public ResponseEntity<GetSignUpInfoList> getSignUpInfoList() {
        log.info("회원가입을 위한 정보 목록 조회");
        return ResponseEntity.ok(signUpService.getSignUpInfoList());
    }


}
