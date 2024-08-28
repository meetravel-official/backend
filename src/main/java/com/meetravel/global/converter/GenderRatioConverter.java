package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.GenderRatio;

public class GenderRatioConverter extends EnumToValueConverter<GenderRatio> {
    public GenderRatioConverter() {
        super(GenderRatio.class);
    }
}
