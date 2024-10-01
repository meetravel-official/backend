package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelKeywordRepository extends JpaRepository<TravelKeywordEntity, Long> {
    boolean existsByMatchingFormAndKeyword(MatchingFormEntity matchingForm, TravelKeyword keyword);;
}
