package com.meetravel.domain.travel.repository;

import com.meetravel.domain.travel.entity.DailyTravelPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyTravelPlaceRepository extends JpaRepository<DailyTravelPlaceEntity, Long> {
}
