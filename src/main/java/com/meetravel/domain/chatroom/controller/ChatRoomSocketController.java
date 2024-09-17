package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.ChatMessage;
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

    @MessageMapping("chat.join")
    public void joinChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Payload ChatMessage chatMessage
    ) {
        chatRoomService.sendJoinedMessage(
                userDetails.getUsername(),
                chatMessage
        );
    }

    @MessageMapping("chat.send")
    public void sendChatMessage(
            @AuthenticationPrincipal UserDetails userDetails,
            @Payload ChatMessage chatMessage
    ) {
        chatRoomService.sendChatMessage(
                userDetails.getUsername(),
                chatMessage
        );
    }
}
