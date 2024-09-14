package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private final Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserChatRoomEntity> userChatRooms = new HashSet<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MatchingFormEntity> matchingForms = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    public ChatRoomEntity() {
        this.id = 0L;
        this.createdAt = LocalDateTime.now(ZoneId.of("UTC"));
    }
}
