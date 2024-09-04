package com.meetravel.domain.sign_up.dto.request;

import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.enums.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    @NotBlank
    private String userId;
    private String name;
    private String nickname;
    private LocalDate birthDate;
    private Gender gender;
    private String phoneNumber;
    private String profileImageUrl;
    private TravelFrequency travelFrequency;
    private ScheduleType scheduleType;
    private PlanningType planningType;
    private MBTI mbti;
    private String hobby;
    private String intro;
    private SocialType socialType;

}
