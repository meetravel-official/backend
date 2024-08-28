package com.meetravel.domain.user.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("ROLE_USER", "사용자"), ADMIN("ROLE_ADMIN", "관리자");

    private final String value;
    private final String desc;

    Role(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }


}
