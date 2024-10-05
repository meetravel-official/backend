package com.meetravel.domain.travel.repository;

import com.meetravel.domain.travel.entity.ShareTravelPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareTravelPlaceRepository extends JpaRepository<ShareTravelPlaceEntity, Long> {
}
