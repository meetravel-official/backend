package com.meetravel.global.converter;

import com.meetravel.domain.place.enums.PlaceType;

public class PlaceTypeConverter extends EnumToValueConverter<PlaceType> {
    public PlaceTypeConverter() { super(PlaceType.class); }
}
