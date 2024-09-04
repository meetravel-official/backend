package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.Gender;

public class GenderConverter extends EnumToValueConverter<Gender> {
    public GenderConverter() {
        super(Gender.class);
    }
}

