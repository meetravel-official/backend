package com.meetravel.domain.matching_form.repository;

import com.meetravel.domain.matching_form.entity.MatchingFormEntity;
import com.meetravel.domain.matching_form.enums.GenderRatio;
import com.meetravel.domain.matching_form.enums.GroupSize;
import com.meetravel.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingFormRepository extends JpaRepository<MatchingFormEntity, Long> {

    Optional<MatchingFormEntity> findByUser(UserEntity user);
    Optional<MatchingFormEntity> findByIdAndUser(Long id, UserEntity userEntity);
    List<MatchingFormEntity> findAllByGroupSizeAndGenderRatioAndStartDateBetweenAndIdNotIn(GroupSize groupSize, GenderRatio genderRatio, LocalDate to, LocalDate from, List<Long> ids);
    List<MatchingFormEntity> findAllByAreaCodeAndGroupSizeAndGenderRatioAndStartDateBetweenAndIdNotIn(String areaCode, GroupSize groupSize, GenderRatio genderRatio, LocalDate to, LocalDate from, List<Long> ids);
}
