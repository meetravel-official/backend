package com.meetravel.domain.chat.dto;

import com.meetravel.domain.chat.entity.ChatMessage;
import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.chat.enums.MessageType;

import java.time.LocalDateTime;

public record ChatMessageSendRequest(
    MessageType messageType,
    ChatRoom chatRoom,
    String sender,
    String message
) {
    public ChatMessage toEntity() {
        return ChatMessage.builder()
            .chatRoom(chatRoom)
            .sender(sender)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
