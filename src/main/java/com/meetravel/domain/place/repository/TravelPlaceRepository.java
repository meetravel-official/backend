package com.meetravel.domain.place.repository;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPlaceRepository extends JpaRepository<TravelPlaceEntity, Long> {

}
