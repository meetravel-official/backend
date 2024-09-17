package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.dto.ChatMessage;
import com.meetravel.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatRoomSocketController {
    private final ChatRoomService chatRoomService;
    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("chat.join")
    public void joinChatRoom(
            @AuthenticationPrincipal UserDetails userDetails,
            @Payload ChatMessage chatMessage
    ) {
        String joinedMessage = chatRoomService.getJoinedMessage(
                userDetails.getUsername(),
                chatMessage.getChatRoomId()
        );

        chatMessage.setMessage(joinedMessage);

        rabbitTemplate.convertAndSend("chat.exchange", "chat.rooms." + chatMessage.getChatRoomId(), chatMessage);
    }
}
