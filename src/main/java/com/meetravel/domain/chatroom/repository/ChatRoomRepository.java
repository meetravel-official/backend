package com.meetravel.domain.chatroom.repository;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    List<ChatRoomEntity> findTop9ByIdNotIn(List<Long> ids, Sort sort);
}
