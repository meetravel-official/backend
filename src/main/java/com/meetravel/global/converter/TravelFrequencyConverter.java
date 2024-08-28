package com.meetravel.global.converter;

import com.meetravel.domain.user.enums.TravelFrequency;

public class TravelFrequencyConverter extends EnumToValueConverter<TravelFrequency> {
    public TravelFrequencyConverter() {
        super(TravelFrequency.class);
    }
}
