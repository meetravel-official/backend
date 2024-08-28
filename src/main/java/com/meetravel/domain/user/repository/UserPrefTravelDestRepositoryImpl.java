package com.meetravel.domain.user.repository;


import com.meetravel.domain.travel_destination.enums.TravelDest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.meetravel.domain.travel_destination.entity.QTravelDestEntity.travelDestEntity;
import static com.meetravel.domain.user.entity.QUserPrefTravelDestEntity.userPrefTravelDestEntity;

@Repository
@RequiredArgsConstructor
public class UserPrefTravelDestRepositoryImpl implements UserPrefTravelDestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TravelDest> getUserPrefTravelDestList(String userId) {
        return queryFactory
                .select(travelDestEntity.travelDest)
                .from(userPrefTravelDestEntity)
                .join(userPrefTravelDestEntity.travelDest, travelDestEntity)
                .where(userIdEq(userId))
                .fetch();
    }

    private BooleanExpression userIdEq(String userId) {
        if (userId == null) {
            return null;
        }
        return userPrefTravelDestEntity.user.userId.eq(userId);
    }
}
