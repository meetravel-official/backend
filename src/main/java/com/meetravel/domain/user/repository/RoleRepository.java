package com.meetravel.domain.user.repository;

import com.meetravel.domain.user.entity.RoleEntity;
import com.meetravel.domain.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(Role role);
}
