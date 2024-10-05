package com.meetravel.domain.travel.repository;

import com.meetravel.domain.travel.entity.TravelPlanKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPlanKeywordRepository extends JpaRepository<TravelPlanKeywordEntity, Long> {
}
