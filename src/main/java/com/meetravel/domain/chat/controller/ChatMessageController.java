package com.meetravel.domain.chat.controller;

import com.meetravel.domain.chat.dto.ChatMessageSendRequest;
import com.meetravel.domain.chat.entity.ChatMessage;
import com.meetravel.domain.chat.service.ChatMessageService;
import com.meetravel.domain.chat.service.ChatRoomService;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.aop.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ChatMessageController {

    @Value("${rabbitmq.exchange.name}")
    private String CHAT_EXCHANGE_NAME;

    private static final String CHAT_QUEUE_NAME = "chat.queue";

    private final RabbitTemplate template;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enterUser(
        @UserSession UserEntity user,
        @DestinationVariable String chatRoomId,
        @Payload ChatMessageSendRequest request
    ) {
        ChatMessage chatMessage = chatMessageService.createChatMessage(user, request);
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatMessage);
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void sendMessage(
        @UserSession UserEntity user,
        @DestinationVariable String chatRoomId,
        @Payload ChatMessageSendRequest request
    ) {
        ChatMessage chatMessage = chatMessageService.createChatMessage(user, request);
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatMessage);
    }

    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receiveMessage(ChatMessage chatMessage) {

    }
}
