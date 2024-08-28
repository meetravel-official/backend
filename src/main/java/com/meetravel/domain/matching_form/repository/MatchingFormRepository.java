package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingFormRepository extends JpaRepository<MatchingFormEntity, Long> {
}
