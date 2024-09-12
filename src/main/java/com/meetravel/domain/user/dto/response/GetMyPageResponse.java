package com.meetravel.domain.user.dto.response;

import com.meetravel.domain.user.enums.MBTI;
import com.meetravel.domain.user.enums.PlanningType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetMyPageResponse {
    private String profileImageUrl;
    private String nickname;
    private LocalDate birthDate;
    private PlanningType planningType;
    private String hobby;
    private MBTI mbti;
    private String intro;

}
