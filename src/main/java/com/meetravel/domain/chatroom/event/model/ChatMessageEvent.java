package com.meetravel.domain.chatroom.event.model;

import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessageEvent {
    private final String chatMessageId;
    private final Long chatRoomId;
    private final String userId;
    private final String message;
    private final ChatMessageType chatMessageType;
    private final String sendAt;

    public ChatMessageEvent(ChatMessageEntity chatMessageEntity) {
        this.chatMessageId = chatMessageEntity.getId();
        this.chatRoomId = chatMessageEntity.getChatRoom().getId();
        this.userId = chatMessageEntity.getUser().getUserId();
        this.message = chatMessageEntity.getMessage();
        this.chatMessageType = ChatMessageType.valueOf(chatMessageEntity.getMessageType());
        this.sendAt = chatMessageEntity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
