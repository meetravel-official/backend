package com.meetravel.domain.chatroom.event.model;

import com.meetravel.domain.chatroom.dto.ChatMessage;

import java.util.List;

public record ChatMessages(List<ChatMessage> chatMessages) {}
