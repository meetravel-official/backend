package com.meetravel.domain.travel.entity;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "share_travel_place")
public class ShareTravelPlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_PLAN_ID")
    private TravelPlanEntity travelPlan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_PLACE_ID")
    private TravelPlaceEntity travelPlace;

    protected ShareTravelPlaceEntity() {
        this.id = 0L;
    }
}