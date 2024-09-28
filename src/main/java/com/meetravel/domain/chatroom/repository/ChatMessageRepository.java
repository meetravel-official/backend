package com.meetravel.domain.chatroom.repository;

import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {
    Page<ChatMessageEntity> findAllByChatRoom(Pageable pageable, ChatRoomEntity chatRoomEntity);
    Page<ChatMessageEntity> findAllByIdBeforeAndChatRoom(Pageable pageable, String id, ChatRoomEntity chatRoomEntity);
}
