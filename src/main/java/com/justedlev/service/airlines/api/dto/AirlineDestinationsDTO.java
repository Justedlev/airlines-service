package com.justedlev.service.airlines.api.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
public class AirlineDestinationsDTO {

    private String airlineName;
    private List<AirlineDestinationDTO> destinations;

}
