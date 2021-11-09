package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Data
public class AircraftDTO {

    private String serialNumber;
    private double price;
    private int maxDistanceKm;

}
