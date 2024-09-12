package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelKeywordRepository extends JpaRepository<TravelKeywordEntity, Long> {
}
