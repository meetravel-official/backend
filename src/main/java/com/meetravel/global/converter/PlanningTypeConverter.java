package com.meetravel.global.converter;


import com.meetravel.domain.user.enums.PlanningType;

public class PlanningTypeConverter extends EnumToValueConverter<PlanningType> {
    public PlanningTypeConverter() {
        super(PlanningType.class);
    }
}
