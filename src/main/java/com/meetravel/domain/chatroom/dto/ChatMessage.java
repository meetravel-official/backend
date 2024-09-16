package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.enums.ChatMessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ChatMessage {
    private final ChatMessageType type;
    private final Long chatRoomId;
    private final String senderId;
    @Setter
    private String message;

    public ChatMessage(ChatMessageType type, Long chatRoomId, String senderId, String message) {
        this.type = type;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.message = message;
    }
}
