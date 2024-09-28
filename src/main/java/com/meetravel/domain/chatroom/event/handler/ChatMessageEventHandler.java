package com.meetravel.domain.chatroom.event.handler;

import com.meetravel.domain.chatroom.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ChatMessageEventHandler {
    private final RabbitTemplate rabbitTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChatMessageEvent(ChatMessage chatMessage) {
        rabbitTemplate.convertAndSend("chat.exchange", "chat.rooms." + chatMessage.getChatRoomId(), chatMessage);
    }
}
