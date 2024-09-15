package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateChatRoomResponse {
    private final Long chatRoomId;
    private final LocalDateTime createdAt;

    public CreateChatRoomResponse(ChatRoomEntity chatRoomEntity) {
        this.chatRoomId = chatRoomEntity.getId();
        this.createdAt = chatRoomEntity.getCreatedAt();
    }
}
