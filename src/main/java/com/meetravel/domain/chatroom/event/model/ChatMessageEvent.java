package com.meetravel.domain.chatroom.event.model;

import com.meetravel.domain.chatroom.dto.ChatMessage;
import com.meetravel.domain.chatroom.enums.ChatMessageType;
import lombok.Getter;

@Getter
public class ChatMessageEvent {
    private final Long chatRoomId;
    private final String userId;
    private final String message;
    private final ChatMessageType chatMessageType;

    public ChatMessageEvent(ChatMessage chatMessage, String userId) {
        this.chatRoomId = chatMessage.getChatRoomId();
        this.userId = userId;
        this.message = chatMessage.getMessage();
        this.chatMessageType = chatMessage.getType();
    }
}
