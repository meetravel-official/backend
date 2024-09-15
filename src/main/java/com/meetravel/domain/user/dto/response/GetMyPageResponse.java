package com.meetravel.domain.user.dto.response;

import com.meetravel.domain.user.enums.MBTI;
import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.ScheduleType;
import com.meetravel.domain.user.enums.TravelFrequency;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetMyPageResponse {
    private String profileImageUrl;
    private String nickname;
    private LocalDate birthDate;
    private TravelFrequency travelFrequency;
    private PlanningType planningType;
    private ScheduleType scheduleType;
    private String hobby;
    private MBTI mbti;
    private String intro;

}
