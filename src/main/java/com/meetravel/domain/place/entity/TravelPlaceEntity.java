package com.meetravel.domain.place.entity;

import com.meetravel.domain.place.enums.PlaceType;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.PlaceTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_place")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPlaceEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLACE_ID", nullable = false, updatable = false)
    private Long id;

    @Convert(converter = PlaceTypeConverter.class)
    @Column(name = "PLACE_TYPE")
    private PlaceType placeType;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

}
