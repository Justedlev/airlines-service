package com.justedlev.service.airlines.repository;

import com.justedlev.service.airlines.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT account FROM UserEntity account WHERE nickname=:nickname")
    Optional<UserEntity> getByName(String nickname);

}
