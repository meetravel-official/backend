package com.meetravel.domain.travel.entity;

import com.meetravel.domain.chatroom.entity.ChatRoomEntity;
import com.meetravel.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "travel_plan")
public class TravelPlanEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    private ChatRoomEntity chatRoom;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelPlanKeywordEntity> travelPlanKeywords;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPlanEntity> dailyPlans;

    @OneToMany(mappedBy = "travelPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShareTravelPlaceEntity> shareTravelPlaces;

    public void removeTravelPlanKeywords(List<TravelPlanKeywordEntity> travelPlanKeywordEntities) {
        this.travelPlanKeywords.removeAll(travelPlanKeywordEntities);
    }

    protected TravelPlanEntity() {
        this.id = 0L;
    }

    public TravelPlanEntity(ChatRoomEntity chatRoomEntity) {
        this();

        this.chatRoom = chatRoomEntity;
    }
}
