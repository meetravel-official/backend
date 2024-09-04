package com.meetravel.domain.chat.repository;

import com.meetravel.domain.chat.entity.ChatRoom;
import com.meetravel.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("select case when count(y) > 0 then true else false end " +
            "from ChatRoom y join y.userList u " +
            "where u = :user")
    boolean existsByUser(@Param("user") UserEntity user);

}
