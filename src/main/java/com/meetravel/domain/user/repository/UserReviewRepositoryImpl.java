package com.meetravel.domain.user.repository;

import com.meetravel.domain.user.enums.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meetravel.domain.user.entity.QReviewEntity.reviewEntity;
import static com.meetravel.domain.user.entity.QUserReviewEntity.userReviewEntity;


@Repository
@RequiredArgsConstructor
public class UserReviewRepositoryImpl implements UserReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<Review> getUserReview(String userId) {
        return queryFactory.select(reviewEntity.review)
                .from(userReviewEntity)
                .join(userReviewEntity.review, reviewEntity).fetchJoin()
                .where(userIdEq(userId))
                .groupBy(reviewEntity.id)
                .orderBy(userReviewEntity.count().desc())
                .fetch();
    }

    private BooleanExpression userIdEq(String userId) {
        if (userId == null) {
            return null;
        }
        return userReviewEntity.user.userId.eq(userId);
    }

}
