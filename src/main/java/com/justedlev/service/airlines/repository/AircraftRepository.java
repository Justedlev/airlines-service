package com.justedlev.service.airlines.repository;

import com.justedlev.service.airlines.repository.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {

    @Query("SELECT aircraft FROM AircraftEntity aircraft WHERE serial_number=:serial_number")
    Optional<AircraftEntity> getBySerialNumber(@Param("serial_number") String serialNumber);

    @Query("SELECT aircraft FROM AircraftEntity aircraft WHERE max_distance_km>=:max_distance")
    Optional<AircraftEntity> getByMaxDistance(@Param("max_distance") int maxDistance);

}
