package com.justedlev.service.airlines.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "airlines")
public class AirlineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private double budget;
    private double latitude;
    private double longitude;
    @OneToMany(mappedBy = "airline", fetch = FetchType.EAGER)
    private Set<AircraftEntity> aircraftList;
    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<DistanceEntity> distances;

    public AirlineEntity(String name, double initialBudget, double latitude, double longitude) {
        this.name = name;
        this.budget = initialBudget;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aircraftList = new HashSet<>();
        this.distances = new HashSet<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setAircraftList(Set<AircraftEntity> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public void setDistances(Set<DistanceEntity> distances) {
        this.distances = distances;
    }

    @PreRemove
    private void preRemove() {
        for (AircraftEntity aircraft : aircraftList) {
            aircraft.setDateStartUse(null);
            aircraft.setAirline(null);
        }
    }
}
