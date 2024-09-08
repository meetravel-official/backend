package com.meetravel.domain.chat.dto;

import com.meetravel.domain.user.entity.UserEntity;
import lombok.Builder;

import java.util.Set;

@Builder
public record ChatRoomUserListResponse(
    Set<UserEntity> userList
) {
}
