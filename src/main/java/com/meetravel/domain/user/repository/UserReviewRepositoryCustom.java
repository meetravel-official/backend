package com.meetravel.domain.user.repository;

import com.meetravel.domain.user.enums.Review;

import java.util.List;

public interface UserReviewRepositoryCustom {
    List<Review> getUserReview(String userId);
}
