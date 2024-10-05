package com.meetravel.domain.travel.repository;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.domain.travel.entity.TravelPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlanEntity, Long> {
    Optional<TravelPlanEntity> findByChatRoom(ChatRoomEntity chatRoom);
}
