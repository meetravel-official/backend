package com.meetravel.domain.travel.entity;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "daily_travel_place")
public class DailyTravelPlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DAILY_PLAN_ID")
    private DailyPlanEntity dailyPlan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_PLACE_ID")
    private TravelPlaceEntity travelPlace;

    protected DailyTravelPlaceEntity() {
        this.id = 0L;
    }

    public DailyTravelPlaceEntity(DailyPlanEntity dailyPlan, TravelPlaceEntity travelPlace) {
        this();

        this.dailyPlan = dailyPlan;
        this.travelPlace = travelPlace;
    }
}
