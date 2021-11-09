package com.justedlev.service.airlines.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "aircraft")
public class AircraftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "serial_number")
    private String serialNumber;
    private double price;
    @Column(name = "max_distance_km")
    private int maxDistanceKm;
    @Column(name = "date_start_use")
    private LocalDate dateStartUse;
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;

    public AircraftEntity(String serialNumber, double price, int maxDistanceKm) {
        this.serialNumber = serialNumber;
        this.price = price;
        this.maxDistanceKm = maxDistanceKm;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMaxDistanceKm(int maxDistanceKm) {
        this.maxDistanceKm = maxDistanceKm;
    }

    public void setDateStartUse(LocalDate dateStartUse) {
        this.dateStartUse = dateStartUse;
    }

    public void setAirline(AirlineEntity airline) {
        this.airline = airline;
    }
}
