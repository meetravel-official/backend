package com.meetravel.domain.chatroom.entity;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.travel.entity.TravelPlanEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChatRoomEntity> userChatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingFormEntity> matchingForms = new ArrayList<>();

    @OneToOne(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private TravelPlanEntity travelPlan;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private final LocalDateTime createdAt;

    public ChatRoomEntity() {
        this.id = 0L;
        this.createdAt = LocalDateTime.now();
    }
}
