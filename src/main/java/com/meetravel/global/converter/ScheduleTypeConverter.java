package com.meetravel.global.converter;


import com.meetravel.domain.user.enums.ScheduleType;

public class ScheduleTypeConverter extends EnumToValueConverter<ScheduleType> {
    public ScheduleTypeConverter() {
        super(ScheduleType.class);
    }
}
