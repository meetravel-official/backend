package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.Duration;

public class DurationConverter extends EnumToValueConverter<Duration> {
    public DurationConverter() {
        super(Duration.class);
    }
}
