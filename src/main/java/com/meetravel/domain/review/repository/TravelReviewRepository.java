package com.meetravel.domain.review.repository;

import com.meetravel.domain.review.entity.TravelReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelReviewRepository extends JpaRepository<TravelReviewEntity, Long> {
}
