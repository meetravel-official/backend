package com.meetravel.domain.chatroom.repository;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
}
