package com.justedlev.service.airlines.repository;

import com.justedlev.service.airlines.repository.entity.AirlineEntity;
import com.justedlev.service.airlines.repository.entity.DistanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistancesRepository extends JpaRepository<DistanceEntity, Long> {

    @Query("SELECT distance FROM DistanceEntity distance WHERE distance.airline.name=:airline_name")
    List<DistanceEntity> getByAirlineName(@Param("airline_name") String airlineName);

    @Query("SELECT distance FROM DistanceEntity distance WHERE distance.airline=:airline")
    List<DistanceEntity> getByAirline(AirlineEntity airline);

    @Query("SELECT distance FROM DistanceEntity distance WHERE distance.airline.name=:airline_name AND distance.destination.name=:destination_name")
    Optional<DistanceEntity> getByAirlineAndDestinationName(@Param("airline_name") String airlineName, @Param("destination_name") String destinationName);

}
