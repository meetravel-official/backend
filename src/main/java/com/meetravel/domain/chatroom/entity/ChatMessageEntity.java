package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.chatroom.enums.ChatMessageType;
import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.utils.UUIDGenerator;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    @Column(name = "MESSAGE_TYPE", nullable = false, updatable = false)
    private String messageType;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    protected ChatMessageEntity() {
        this.id = UUIDGenerator.newUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public ChatMessageEntity(UserEntity user, ChatRoomEntity chatRoom, String message, ChatMessageType messageType) {
        this();

        this.user = user;
        this.chatRoom = chatRoom;
        this.message = message;
        this.messageType = messageType.name();
    }
}
