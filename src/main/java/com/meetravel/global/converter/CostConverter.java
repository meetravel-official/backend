package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.Cost;

public class CostConverter extends EnumToValueConverter<Cost> {
    public CostConverter() {
        super(Cost.class);
    }
}
