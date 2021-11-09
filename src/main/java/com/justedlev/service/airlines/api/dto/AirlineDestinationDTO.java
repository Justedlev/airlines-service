package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
public class AirlineDestinationDTO {

    private DestinationDTO destination;
    private double distance;

}
