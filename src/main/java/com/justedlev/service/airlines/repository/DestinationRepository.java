package com.justedlev.service.airlines.repository;

import com.justedlev.service.airlines.repository.entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {

    @Query("SELECT destination FROM DestinationEntity destination WHERE latitude=:latitude AND longitude=:longitude")
    Optional<DestinationEntity> getByLocation(double latitude, double longitude);

    @Query("SELECT destination FROM DestinationEntity destination WHERE name=:name")
    Optional<DestinationEntity> getByName(String name);
}
