package com.meetravel.domain.place.entity;

import com.meetravel.domain.place.enums.PlaceType;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.PlaceTypeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.annotations.NotNull;

@Entity
@Table(name = "travel_place")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlaceEntity extends BaseEntity {

    @Id
    @Column(name = "PLACE_ID", nullable = false, updatable = false)
    @NotBlank
    private String placeId; // 관광공사 API의 contentId

    @Convert(converter = PlaceTypeConverter.class)
    @Column(name = "PLACE_TYPE", nullable = false)
    @NotBlank
    private PlaceType placeType; // 장소 유형

    @Column(name = "TITLE", nullable = false)
    @NotBlank
    private String title; // 여행지 이름(제목)

    @Column(name = "ADDR1", nullable = false)
    @NotBlank
    private String address1; // 주소

    @Column(name = "ADDR2")
    private String address2; // 상세 주소

    @Column(name = "IMAGE")
    private String imageUrl; // 이미지

}
