package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private final Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChatRoomEntity> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingFormEntity> matchingForms = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    public ChatRoomEntity() {
        this.id = 0L;
        this.createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
