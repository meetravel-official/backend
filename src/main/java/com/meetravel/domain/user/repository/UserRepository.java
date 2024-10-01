package com.meetravel.domain.user.repository;


import com.meetravel.domain.user.entity.UserEntity;
import com.meetravel.domain.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByNickname(String nickName);
    List<UserEntity> findAllBySocialType(SocialType socialType);
}
