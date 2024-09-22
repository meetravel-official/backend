package com.meetravel.domain.user.dto.request;

import com.meetravel.domain.user.enums.MBTI;
import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.ScheduleType;
import com.meetravel.domain.user.enums.TravelFrequency;
import lombok.Getter;

@Getter
public class UpdateMyPageInfoRequest {

    private TravelFrequency travelFrequency;
    private PlanningType planningType;
    private ScheduleType scheduleType;
    private String hobby;
    private MBTI mbti;
    private String intro;
}
