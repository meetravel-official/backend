package com.meetravel.domain.place.repository;

import com.meetravel.domain.place.entity.TravelPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPlaceRepository extends JpaRepository<TravelPlaceEntity, String> {
    List<TravelPlaceEntity> findByPlaceIdIn(List<String> placeIds);
}
