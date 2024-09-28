package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessage {
    private final String chatMessageId;
    private final String userId;
    private final Long chatRoomId;
    private final ChatMessageType type;
    private final String message;
    private final String sendAt;

    public ChatMessage(String userId, Long chatRoomId, ChatMessageType type, String message) {
        this.chatMessageId = null;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.type = type;
        this.message = message;
        this.sendAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public ChatMessage(ChatMessageEntity chatMessageEntity) {
        this.chatMessageId = chatMessageEntity.getId();
        this.userId = chatMessageEntity.getUser().getUserId();
        this.chatRoomId = chatMessageEntity.getChatRoom().getId();
        this.type = ChatMessageType.valueOf(chatMessageEntity.getMessageType());
        this.message = chatMessageEntity.getMessage();
        this.sendAt = chatMessageEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
