package com.meetravel.domain.travel.repository;

import com.meetravel.domain.travel.entity.DailyPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlanEntity, Long> {
}
