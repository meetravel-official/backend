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
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoomEntity chatRoom;

    @Column(name = "JOINED_AT", nullable = false, updatable = false)
    private final LocalDateTime joinedAt;

    @Column(name = "LEAVE_AT")
    private LocalDateTime leaveAt;

    protected UserChatRoomEntity() {
        this.id = 0L;
        this.joinedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public UserChatRoomEntity(UserEntity user, ChatRoomEntity chatRoom) {
        this();

        this.user = user;
        this.chatRoom = chatRoom;
    }
}
