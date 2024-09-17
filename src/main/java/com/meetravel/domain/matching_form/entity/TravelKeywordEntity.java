package com.meetravel.domain.matching_form.entity;

import com.meetravel.domain.matching_form.enums.TravelKeyword;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.TravelKeywordConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_keyword")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelKeywordEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAVEL_KEYWORD_ID")
    private Long id;

    @Column(name = "TRAVEL_KEYWORD")
    @Convert(converter = TravelKeywordConverter.class)
    private TravelKeyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATCHING_FORM_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private MatchingFormEntity matchingForm;

    public void setMatchingForm(MatchingFormEntity matchingForm){
        this.matchingForm = matchingForm;
    }
}
