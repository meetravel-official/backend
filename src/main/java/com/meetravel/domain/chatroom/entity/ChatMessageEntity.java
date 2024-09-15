package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.utils.UUIDGenerator;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "chat_message")
public class ChatMessageEntity {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private final String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoomEntity chatRoom;

    @Column(name = "MESSAGE", nullable = false, updatable = false)
    private String message;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    public ChatMessageEntity() {
        this.id = UUIDGenerator.newUUID().toString();
        this.createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
