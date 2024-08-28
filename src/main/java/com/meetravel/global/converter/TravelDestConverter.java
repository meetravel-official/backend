package com.meetravel.global.converter;

import com.meetravel.domain.travel_destination.enums.TravelDest;

public class TravelDestConverter extends EnumToValueConverter<TravelDest> {
    public TravelDestConverter() {
        super(TravelDest.class);
    }
}
