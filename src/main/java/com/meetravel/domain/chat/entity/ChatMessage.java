package com.meetravel.domain.chat.entity;

import com.meetravel.domain.chat.enums.MessageType;
import com.meetravel.domain.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @OneToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType messageType;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public void update(ChatMessage chatMessage) {
        this.chatRoom = chatMessage.chatRoom != null ? chatMessage.chatRoom : chatRoom;
        this.user = chatMessage.user != null ? chatMessage.user : user;
        this.messageType = chatMessage.messageType != null ? chatMessage.messageType : messageType;
        this.message = chatMessage.message != null ? chatMessage.message : message;
    }
}
