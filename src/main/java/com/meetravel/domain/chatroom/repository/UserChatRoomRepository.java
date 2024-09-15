package com.meetravel.domain.chatroom.repository;

import com.meetravel.domain.chatroom.entity.UserChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRoomEntity, Long> {
}
