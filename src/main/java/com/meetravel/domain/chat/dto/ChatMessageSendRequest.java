package com.meetravel.domain.chat.dto;

import com.meetravel.domain.chat.enums.MessageType;

public record ChatMessageSendRequest(
    MessageType messageType,
    Long chatRoomId,
    String message
) {
}
