package com.meetravel.domain.user.service;

import com.meetravel.domain.auth.service.KakaoApiFeignClient;
import com.meetravel.domain.token.service.RefreshTokenService;
import com.meetravel.domain.user.dto.request.UpdateMyPageInfoRequest;
import com.meetravel.domain.user.dto.response.GetMyPageResponse;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import com.meetravel.global.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final KakaoApiFeignClient kakaoApiFeignClient;

    @Value("${auth.kakao.admin_key}")
    private String adminKey;

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
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .travelFrequency(user.getTravelFrequency())
                .planningType(user.getPlanningType())
                .scheduleType(user.getScheduleType())
                .hobby(user.getHobby())
                .mbti(user.getMbti())
                .intro(user.getIntro())
                .build();
    }

    @Transactional
    public void updateProfileImage(String userId, String profileImageUrl) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        user.updateProfileImage(profileImageUrl);
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

        // 카카오 연결 끊기
        String authorizationHeader = "KakaoAK " + adminKey;

        Long kakaoUserId = Long.valueOf(user.getUserId().split("@")[0]); // '@kakao를 제외한 고유번호 추출'

        kakaoApiFeignClient.unlinkUser(authorizationHeader, "user_id", kakaoUserId);

        userRepository.delete(user);
    }

}
