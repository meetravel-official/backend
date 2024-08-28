package com.meetravel.global.converter;

import com.meetravel.domain.matching_form.enums.GroupSize;

public class GroupSizeConverter extends EnumToValueConverter<GroupSize> {
    public GroupSizeConverter() {
        super(GroupSize.class);
    }
}
