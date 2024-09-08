package com.meetravel.domain.chat.service;

import com.meetravel.domain.chat.dto.ChatRoomCreateRequest;
import com.meetravel.domain.chat.dto.ChatRoomListResponse;
import com.meetravel.domain.chat.dto.ChatRoomResponse;
import com.meetravel.domain.chat.dto.ChatRoomUserListResponse;
import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.chat.repository.ChatRoomRepository;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.exception.BadRequestException;
import com.meetravel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomResponse createChatRoom(UserEntity user, ChatRoomCreateRequest request) {
        // 신청 회원이 이미 채팅방에 참여 중이면 에러 반환
        if (chatRoomRepository.existsByUser(user)) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_INSIDE_CHATROOM);
        }

        // 채팅방 개설 후 추가
        ChatRoom chatRoom = request.toEntity();
        chatRoom.addUsers(Collections.singleton(user));
        chatRoomRepository.save(chatRoom);
        return ChatRoomResponse.fromChatRoom(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoomResponse getChatRoomResponse(Long chatRoomId) {
        return ChatRoomResponse.fromChatRoom(
            getChatRoom(chatRoomId)
        );
    }

    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BadRequestException(ErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public ChatRoomListResponse getAllChatRooms() {
        return ChatRoomListResponse.fromChatRoomList(chatRoomRepository.findAll());
    }

    public void updateTitle(Long chatRoomId, String title) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        chatRoom.updateTitle(title);
    }

    public void addUser(Long chatRoomId, UserEntity user) {
        addUsers(chatRoomId, Collections.singleton(user));
    }

    public void addUsers(Long chatRoomId, Set<UserEntity> users) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        chatRoom.addUsers(users);
    }

    public void removeUser(Long chatRoomId, UserEntity user) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        chatRoom.removeUser(user);
    }

    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }

    @Transactional(readOnly = true)
    public ChatRoomUserListResponse getUserList(Long chatRoomId) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        Set<UserEntity> userList = chatRoom.getUserList();
        return ChatRoomUserListResponse.builder()
            .userList(userList)
            .build();
    }
}
