package com.meetravel.domain.chatroom.repository;

import com.meetravel.domain.chatroom.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
}
