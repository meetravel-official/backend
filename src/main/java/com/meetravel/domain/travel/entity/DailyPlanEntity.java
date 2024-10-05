package com.meetravel.domain.travel.entity;

import com.meetravel.global.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@Table(name = "daily_plan")
public class DailyPlanEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_PLAN_ID")
    private TravelPlanEntity travelPlan;

    @Column(name = "PLAN_DATE")
    private LocalDate planDate;

    @Column(name = "MEET_PLACE")
    private String meetPlace;

    @Column(name = "MEET_TIME")
    private String meetTime;

    @OneToMany(mappedBy = "dailyPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyTravelPlaceEntity> dailyTravelPlaces;

    protected DailyPlanEntity() {
        this.id = 0L;
    }
}
