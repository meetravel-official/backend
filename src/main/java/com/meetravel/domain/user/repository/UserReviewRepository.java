package com.meetravel.domain.user.repository;

import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.entity.UserReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserReviewRepository extends JpaRepository<UserReviewEntity, Long>, UserReviewRepositoryCustom {
    List<UserReviewEntity> findAllByUser(UserEntity user);
}
