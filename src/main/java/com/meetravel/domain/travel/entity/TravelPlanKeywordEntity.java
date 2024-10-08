package com.meetravel.domain.travel.entity;

import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.TravelKeywordConverter;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "travel_plan_keyword")
public class TravelPlanKeywordEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private final Long id;

    @Column(name = "TRAVEL_KEYWORD")
    @Convert(converter = TravelKeywordConverter.class)
    private TravelKeyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_PLAN_ID")
    private TravelPlanEntity travelPlan;

    public TravelPlanKeywordEntity deleteMapping() {
        this.travelPlan = null;

        return this;
    }

    protected TravelPlanKeywordEntity() {
        this.id = 0L;
    }

    public TravelPlanKeywordEntity(
            TravelPlanEntity travelPlanEntity,
            TravelKeyword travelKeyword
    ) {
        this();

        this.keyword = travelKeyword;
        this.travelPlan = travelPlanEntity;
    }
}
