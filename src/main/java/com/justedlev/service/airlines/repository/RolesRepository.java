package com.justedlev.service.airlines.repository;

import java.util.Optional;

import com.justedlev.service.airlines.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE role=:role")
    Optional<RoleEntity> getByRole(String role);

}
