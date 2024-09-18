package com.meetravel.domain.review.entity;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import com.meetravel.domain.place.enums.PlaceType;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.PlaceTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_review")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID", nullable = false, updatable = false)
    private Long id;

    @Convert(converter = PlaceTypeConverter.class)
    @Column(name = "PLACE_TYPE")
    private PlaceType placeType; // 관광, 식당, 숙박

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID")
    private TravelPlaceEntity travelPlace; // 여행 장소

    @Builder.Default
    @Column(name = "LIKE_CNT")
    private int likeCount = 0;

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }

}
