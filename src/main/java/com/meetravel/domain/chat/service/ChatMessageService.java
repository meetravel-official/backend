package com.meetravel.domain.chat.service;

import com.meetravel.domain.chat.dto.ChatMessageSendRequest;
import com.meetravel.domain.chat.entity.ChatMessage;
import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.chat.repository.ChatRoomRepository;
import com.meetravel.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage createChatMessage(UserEntity user, ChatMessageSendRequest request) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(request.chatRoomId());

        // 해당 채팅방에 존재하는 유저인지 검사
        boolean exists = chatRoom.checkUserExists(user);
        if (!exists) {
            log.error("User not found in the chat room: {}", request.chatRoomId());
        }

        return ChatMessage.builder()
            .chatRoom(chatRoom)
            .user(user)
            .message(request.message())
            .messageType(request.messageType())
            .timestamp(LocalDateTime.now())
            .build();
    }
}
