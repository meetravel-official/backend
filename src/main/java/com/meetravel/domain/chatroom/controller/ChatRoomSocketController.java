package com.meetravel.domain.chatroom.controller;

import com.meetravel.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatRoomSocketController {
    private final ChatRoomService chatRoomService;
    private final RabbitTemplate rabbitTemplate;

    @MessageMapping("/chat/join")
//    @SendTo("/topic/chat-rooms/{chatRoomId}")
    public void joinChatRoom(
//            @AuthenticationPrincipal UserDetails userDetails,
            @DestinationVariable Long chatRoomId
    ) {
        String joinedMessage = chatRoomService.getJoinedMessage(
//                userDetails.getUsername(),
                "1",
                chatRoomId
        );

        rabbitTemplate.convertAndSend("/chat/exchange", "chat-rooms/" + chatRoomId, joinedMessage);
    }
}
