package com.meetravel.domain.matching_form.entity;

import com.meetravel.domain.matching_form.enums.Cost;
import com.meetravel.domain.matching_form.enums.Duration;
import com.meetravel.domain.matching_form.enums.GenderRatio;
import com.meetravel.domain.matching_form.enums.GroupSize;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "matching_application_form")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingFormEntity extends BaseEntity {

    @Id
    @Column(name = "MATCHING_APPL_FORM_ID")
    private Long id;

    @Column(name = "USER_ID", unique = true)
    private String userId;

    @Column(name = "DURATION")
    @Convert(converter = DurationConverter.class)
    private Duration duration;

    @Column(name = "START_DT")
    private LocalDate startDate;

    @Column(name = "END_DT")
    private LocalDate endDate;

    @Column(name = "GROUP_SIZE")
    @Convert(converter = GroupSizeConverter.class)
    private GroupSize groupSize;

    @Column(name = "GENDER_RATIO")
    @Convert(converter = GenderRatioConverter.class)
    private GenderRatio genderRatio;

    @Column(name = "COST")
    @Convert(converter = CostConverter.class)
    private Cost cost;

}
