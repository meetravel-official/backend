package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.ChatSendRequest;
import com.meetravel.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatRoomSocketController {
    private final ChatRoomService chatRoomService;

    @MessageMapping("chat.send")
    public void sendChatMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @Payload ChatSendRequest chatSendRequest
    ) {
        chatRoomService.sendChatMessage(
                userDetails.getUsername(),
                chatSendRequest
        );
    }
}
