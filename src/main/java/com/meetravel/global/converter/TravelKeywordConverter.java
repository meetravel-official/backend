package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.TravelKeyword;

public class TravelKeywordConverter extends EnumToValueConverter<TravelKeyword>{
    public TravelKeywordConverter() {
        super(TravelKeyword.class);
    }
}
