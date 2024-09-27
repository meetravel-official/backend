package com.meetravel.domain.user.entity;

import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.dto.request.UpdateMyPageInfoRequest;
import com.meetravel.domain.user.enums.*;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PASSWORD")
    @Builder.Default
    private String password = ""; // Default 값으로 빈 문자열 설정

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "GENDER")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    @Column(name = "TRAVEL_FREQ")
    @Convert(converter = TravelFrequencyConverter.class)
    private TravelFrequency travelFrequency;

    @Column(name = "SCHEDULE_TYPE")
    @Convert(converter = ScheduleTypeConverter.class)
    private ScheduleType scheduleType;

    @Column(name = "PLANNING_TYPE")
    @Convert(converter = PlanningTypeConverter.class)
    private PlanningType planningType;

    @Column(name = "MBTI")
    @Enumerated(EnumType.STRING)
    private MBTI mbti;

    @Column(name = "HOBBY")
    private String hobby;

    @Column(name = "INTRO")
    private String intro;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOCIAL_TYPE")
    private SocialType socialType; // KAKAO

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserRoleEntity> userRoles = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MatchingFormEntity> matchingFormEntities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<UserChatRoomEntity> userChatRooms = new ArrayList<>();

    // 권한 부여
    public void addUserRole(UserRoleEntity userRole) {
        userRoles.add(userRole);
        userRole.setUser(this);
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateMyPageInfo(UpdateMyPageInfoRequest request) {
        this.travelFrequency = request.getTravelFrequency();
        this.planningType = request.getPlanningType();
        this.scheduleType = request.getScheduleType();
        this.hobby = request.getHobby();
        this.intro = request.getIntro();
    }

}
