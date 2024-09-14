package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.user.entity.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "user_chat_room")
public class UserChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoom;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private final LocalDateTime joinedAt;

    @Column(name = "leave_at")
    private LocalDateTime leaveAt;

    public UserChatRoomEntity() {
        this.id = 0L;
        this.joinedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
