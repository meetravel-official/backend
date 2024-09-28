package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.entity.TravelKeywordEntity;
import com.meetravel.domain.matching_form.enums.TravelKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelKeywordRepository extends JpaRepository<TravelKeywordEntity, Long> {

    @Modifying(flushAutomatically = true)
    @Query(value = "delete from TravelKeywordEntity k where k.id in :travelKeywordIdList")
    void deleteAllByTravelKeywordId(@Param("travelKeywordIdList") List<Long> travelKeywordIdList);

    boolean existsByMatchingFormAndKeyword(MatchingFormEntity matchingForm, TravelKeyword keyword);;
}
