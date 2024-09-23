package com.meetravel.domain.sign_up.dto.request;

import com.meetravel.domain.matching_form.enums.Gender;
import com.meetravel.domain.user.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    @NotBlank
    private String userId;
    @NotBlank @Size(min = 2, max = 6, message = "길이는 2자 이상 6자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "한글 또는 영문만 입력 가능합니다.")
    private String name;

    @NotBlank @Size(min = 2, max = 6, message = "길이는 2자 이상 6자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "한글 또는 영문만 입력 가능합니다.")
    private String nickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
