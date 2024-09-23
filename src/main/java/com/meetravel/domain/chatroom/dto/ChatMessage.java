package com.meetravel.domain.chatroom.dto;

import com.meetravel.domain.chatroom.enums.ChatMessageType;

public record ChatMessage(String userId, Long chatRoomId, ChatMessageType type, String message) {}
