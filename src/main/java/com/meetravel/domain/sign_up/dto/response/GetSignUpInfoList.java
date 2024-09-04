package com.meetravel.domain.sign_up.dto.response;

import com.meetravel.domain.user.enums.PlanningType;
import com.meetravel.domain.user.enums.ScheduleType;
import com.meetravel.domain.user.enums.TravelFrequency;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class GetSignUpInfoList {

    private List<TravelFrequencyInfo> travelFrequencies;
    private List<ScheduleTypeInfo> scheduleTypes;
    private List<PlanningTypeInfo> planningTypes;

    @Getter
    @Builder
    public static class TravelFrequencyInfo {
        private TravelFrequency travelFrequency;
        private String value;
    }

    @Getter
    @Builder
    public static class ScheduleTypeInfo {
        private ScheduleType scheduleType;
        private String value;
    }

    @Getter
    @Builder
    public static class PlanningTypeInfo {
        private PlanningType planningType;
        private String value;
    }

}
