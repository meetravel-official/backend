package com.meetravel.domain.sign_up.controller;

import com.meetravel.domain.sign_up.dto.request.SignUpRequest;
import com.meetravel.domain.sign_up.dto.response.GetSignUpInfoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "회원가입 관련 API")
public interface SignUpControllerDoc {
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    void signUp(@RequestBody @Valid SignUpRequest signUpRequest);

    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 확인을 진행합니다.")
    ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickName);
    @Operation(summary = "선호여행지 선택을 위한 여행지 목록 조회", description = "선호여행지 선택을 위한 여행지 목록을 조회합니다.")
    ResponseEntity<GetSignUpInfoList> getSignUpInfoList();

}
