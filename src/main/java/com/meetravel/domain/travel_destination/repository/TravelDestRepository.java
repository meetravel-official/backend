package com.meetravel.domain.travel_destination.repository;

import com.meetravel.domain.travel_destination.entity.TravelDestEntity;
import com.meetravel.domain.travel_destination.enums.TravelDest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelDestRepository extends JpaRepository<TravelDestEntity, Long> {
    Optional<TravelDestEntity> findById(Long travelDestId);
}
