package com.meetravel.domain.user.service;

import com.meetravel.domain.token.service.RefreshTokenService;
import com.meetravel.domain.user.dto.request.UpdateMyPageInfoRequest;
import com.meetravel.domain.user.dto.response.GetMyPageResponse;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import com.meetravel.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    /**
     * 마이페이지 조회
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public GetMyPageResponse getMyPage(String userId) {

        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return GetMyPageResponse.builder()
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .birthDate(user.getBirthDate())
                .travelFrequency(user.getTravelFrequency())
                .planningType(user.getPlanningType())
                .scheduleType(user.getScheduleType())
                .hobby(user.getHobby())
                .mbti(user.getMbti())
                .intro(user.getIntro())
                .build();
    }


    /**
     * 마이페이지 닉네임 수정
     * @param userId
     * @param nickname
     */
    @Transactional
    public void updateNickname(String userId, String nickname) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        user.updateNickname(nickname);
    }

    /**
     * 마이페이지 정보 수정
     * @param userId
     * @param request
     */
    @Transactional
    public void updateMyPageInfo(String userId, UpdateMyPageInfoRequest request) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        user.updateMyPageInfo(request);
    }

    /**
     * 회원 로그아웃
     *
     * @param refreshToken
     */
    @Transactional
    public void logout(String refreshToken) {
        // refreshToken DB에서 삭제
        refreshTokenService.deleteRefreshToken(jwtService.getUserId(refreshToken));

    }

    /**
     * 회원 탈퇴
     * @param userId
     */
    @Transactional
    public void deleteUser(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

}
