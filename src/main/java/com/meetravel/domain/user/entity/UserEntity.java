package com.meetravel.domain.user.entity;

import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.enums.MBTI;
import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.ScheduleType;
import com.meetravel.domain.user.enums.SocialType;
import com.meetravel.domain.user.enums.TravelFrequency;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.GenderConverter;
import com.meetravel.global.converter.PlanningTypeConverter;
import com.meetravel.global.converter.ScheduleTypeConverter;
import com.meetravel.global.converter.TravelFrequencyConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name = "TRAVEL_COUNT")
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

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    @Builder.Default
    private List<UserReviewEntity> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    // 권한 부여
    public void addUserRole(UserRoleEntity userRole) {
        userRoles.add(userRole);
        userRole.setUser(this);
    }

}
