package com.meetravel.domain.chat.controller;

import com.meetravel.domain.chat.dto.ChatRoomCreateRequest;
import com.meetravel.domain.chat.service.ChatService;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.aop.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<?> createChatRoom(@UserSession UserEntity user, ChatRoomCreateRequest request) {
        return chatService.createChatRoom(user, request);
    }
}
