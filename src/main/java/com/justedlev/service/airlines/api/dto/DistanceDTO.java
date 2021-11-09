package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
public class DistanceDTO {

    private AirlineDTO airline;
    private DestinationDTO destination;
    private double distance;

}
