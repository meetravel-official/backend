package com.meetravel.domain.chat.service;

import com.meetravel.domain.chat.dto.ChatRoomCreateRequest;
import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.chat.repository.ChatRoomRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public ResponseEntity<?> createChatRoom(UserEntity user, ChatRoomCreateRequest request) {
        // 신청 회원이 이미 채팅방에 참여 중이면 에러 반환
        if (chatRoomRepository.existsByUser(user)) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_INSIDE_CHATROOM);
        }

        // 채팅방 개설 후 추가
        ChatRoom chatRoom = request.toEntity();
        chatRoom.addUsers(Collections.singleton(user));
        chatRoomRepository.save(chatRoom);
        return null;
    }
}
