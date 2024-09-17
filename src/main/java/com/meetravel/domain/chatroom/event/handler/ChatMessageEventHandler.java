package com.meetravel.domain.chatroom.event.handler;

import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.chatroom.event.model.ChatMessageEvent;
import com.meetravel.domain.chatroom.repository.ChatMessageRepository;
import com.meetravel.domain.chatroom.repository.ChatRoomRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.repository.UserRepository;
import com.meetravel.global.exception.ErrorCode;
import com.meetravel.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ChatMessageEventHandler {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleChatMessageEvent(ChatMessageEvent chatMessageEvent) {
        UserEntity userEntity = userRepository.findById(chatMessageEvent.getUserId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findById(chatMessageEvent.getChatRoomId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        ChatMessageEntity chatMessageEntity = new ChatMessageEntity(
                userEntity,
                chatRoomEntity,
                chatMessageEvent.getMessage(),
                chatMessageEvent.getChatMessageType()
        );

        chatMessageRepository.save(chatMessageEntity);
    }
}
