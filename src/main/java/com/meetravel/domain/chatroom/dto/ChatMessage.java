package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.enums.ChatMessageType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessage {
    private final String userId;
    private final Long chatRoomId;
    private final ChatMessageType type;
    private final String message;
    private final String sendAt;

    public ChatMessage(String userId, Long chatRoomId, ChatMessageType type, String message) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.type = type;
        this.message = message;
        this.sendAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
