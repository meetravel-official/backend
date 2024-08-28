package com.meetravel.domain.travel_destination.entity;

import com.meetravel.domain.travel_destination.enums.TravelDest;
import com.meetravel.global.audit.BaseEntity;
import com.meetravel.global.converter.TravelDestConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "travel_dest")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDestEntity extends BaseEntity {

    @Id
    @Column(name = "TRAVEL_DEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRAVEL_DEST")
    @Convert(converter = TravelDestConverter.class)
    private TravelDest travelDest;


}
