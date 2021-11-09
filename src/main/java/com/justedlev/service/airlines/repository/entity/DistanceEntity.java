package com.justedlev.service.airlines.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "distances")
public class DistanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private DestinationEntity destination;
    private double distance;

    public DistanceEntity(AirlineEntity airline, DestinationEntity destination, double distance) {
        this.airline = airline;
        this.destination = destination;
        this.distance = distance;
    }
}
