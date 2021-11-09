package com.justedlev.service.airlines.repository;

import com.justedlev.service.airlines.repository.entity.AirlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<AirlineEntity, Long> {

    @Query("SELECT airline FROM AirlineEntity airline WHERE name=:name")
    Optional<AirlineEntity> getByName(String name);

}
