package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingFormRepository extends JpaRepository<MatchingFormEntity, Long> {
    Optional<MatchingFormEntity> findByIdAndUser(Long id, UserEntity userEntity);
}
