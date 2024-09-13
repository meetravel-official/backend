package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.global.utils.UUIDGenerator;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessageEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private final String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserEntity user;

    @Column(name = "message", nullable = false, updatable = false)
    private String message;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public ChatMessageEntity() {
        this.id = UUIDGenerator.newUUID().toString();
    }
}
