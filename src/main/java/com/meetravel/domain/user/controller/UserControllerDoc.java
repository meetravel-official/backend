package com.meetravel.domain.user.controller;

import com.meetravel.domain.user.dto.request.UpdateMyPageInfoRequest;
import com.meetravel.domain.user.dto.request.UpdateNicknameRequest;
import com.meetravel.domain.user.dto.request.UpdateProfileImageRequest;
import com.meetravel.domain.user.dto.response.GetMyPageResponse;
import com.meetravel.domain.user.dto.response.GetOtherUserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "사용자(유저) API")
public interface UserControllerDoc {

    @Operation(summary = "마이페이지 조회", description = "마이페이지를 조회합니다.")
    ResponseEntity<GetMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "다른 사람의 프로필 조회", description = "다른 사람의 프로필을 조회합니다.")
    ResponseEntity<GetOtherUserProfileResponse> getOtherUserProfile(@RequestParam String otherUserId);

    @Operation(summary = "프로필 이미지 수정", description = "프로필 이미지를 수정합니다.")
    void updateProfileImage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateProfileImageRequest request);

    @Operation(summary = "닉네임 수정", description = "닉네임을 수정합니다.")
    void updateNickname(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateNicknameRequest request);

    @Operation(summary = "마이페이지 부가 정보를 수정", description = "마이페이지 부가 정보를 수정합니다.")
    void updateMyPageInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody @Valid UpdateMyPageInfoRequest request);

    @Operation(summary = "회원 로그아웃", description = "회원 로그아웃을 진행합니다.")
    void logout(HttpServletRequest request);

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    void deleteUser(@AuthenticationPrincipal UserDetails userDetails) ;
}
