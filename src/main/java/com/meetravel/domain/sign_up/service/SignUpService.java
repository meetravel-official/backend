package com.meetravel.domain.sign_up.service;


import com.meetravel.domain.sign_up.dto.request.SignUpRequest;
import com.meetravel.domain.sign_up.dto.response.GetSignUpInfoList;
import com.meetravel.domain.user.entity.RoleEntity;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.entity.UserRoleEntity;
import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.Role;
import com.meetravel.domain.user.enums.ScheduleType;
import com.meetravel.domain.user.enums.TravelFrequency;
import com.meetravel.domain.user.repository.RoleRepository;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * 회원가입
     *
     * @param signUpRequest
     */
    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        // 이미 존재하는 회원 ID인지 검증
        if (userRepository.findByUserId(signUpRequest.getUserId()).isPresent()) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS_USER_ID);
        }


        // 유저 엔티티 생성
        UserEntity user = UserEntity.builder()
                .userId(signUpRequest.getUserId())
                .name(signUpRequest.getName())
                .nickname(signUpRequest.getNickname())
                .birthDate(signUpRequest.getBirthDate())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .profileImageUrl(signUpRequest.getProfileImageUrl())
                .travelFrequency(signUpRequest.getTravelFrequency())
                .scheduleType(signUpRequest.getScheduleType())
                .planningType(signUpRequest.getPlanningType())
                .mbti(signUpRequest.getMbti())
                .hobby(signUpRequest.getHobby())
                .intro(signUpRequest.getIntro())
                .socialType(signUpRequest.getSocialType())
                .build();


        // 회원가입
        /** 그냥 save만 해버리고 user 변수로 받지않고 선호 여행지 추가 메소드에 넘기면 안된다.(Transient : JPA가 아예 인지를 하지 못하는 상태로 인식된다)
         ** 객체는 메모리에 있지만 아직 데이터베이스에 저장되지 않았으며, Persistence Context에도 존재하지 않는 상태이다
         *
         */
        user = userRepository.save(user);


        // 회원 권한 부여
        this.addUserRole(user);
        // 회원 선호여행지 추가

    }

    /**
     * 회원 권한 부여 (일반 사용자)
     *
     * @param user
     */
    private void addUserRole(UserEntity user) {

        // 일반 사용자 권한 부여
        RoleEntity role = roleRepository.findByRole(Role.USER)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ROLE_NOT_FOUND));


        UserRoleEntity userRole = UserRoleEntity.builder()
                .user(user)
                .role(role)
                .build();

        user.addUserRole(userRole);
    }

    /**
     * 회원가입 시 필요한 목록 조회
     *
     * @return
     */
    @Transactional(readOnly = true)
    public GetSignUpInfoList getSignUpInfoList() {

        // 여행 횟수 
        List<GetSignUpInfoList.TravelFrequencyInfo> travelFrequencies = this.getTravelFrequencyInfo();

        // 여행 취향 
        List<GetSignUpInfoList.ScheduleTypeInfo> scheduleTypes = this.getScheduleTypeInfo();
        List<GetSignUpInfoList.PlanningTypeInfo> planningTypes = this.getPlanningTypeInfo();


        return GetSignUpInfoList.builder()
                .travelFrequencies(travelFrequencies)
                .scheduleTypes(scheduleTypes)
                .planningTypes(planningTypes)
                .build();
    }


    /**
     * 여행 횟수 목록 가져오기
     *
     * @return
     */
    private List<GetSignUpInfoList.TravelFrequencyInfo> getTravelFrequencyInfo() {
        return Arrays.stream(TravelFrequency.values())
                .map(frequency -> GetSignUpInfoList.TravelFrequencyInfo.builder()
                        .travelFrequency(frequency)
                        .value(frequency.getValue())
                        .build())
                .toList();
    }

    /**
     * 여행 취향 1 목록 가져오기
     *
     * @return
     */
    private List<GetSignUpInfoList.ScheduleTypeInfo> getScheduleTypeInfo() {
        return Arrays.stream(ScheduleType.values())
                .map(type -> GetSignUpInfoList.ScheduleTypeInfo.builder()
                        .scheduleType(type)
                        .value(type.getValue())
                        .build())
                .toList();
    }

    /**
     * 여행 취향 2 목록 가져오기
     *
     * @return
     */
    private List<GetSignUpInfoList.PlanningTypeInfo> getPlanningTypeInfo() {
        return Arrays.stream(PlanningType.values())
                .map(type -> GetSignUpInfoList.PlanningTypeInfo.builder()
                        .planningType(type)
                        .value(type.getValue())
                        .build())
                .toList();
    }

}
