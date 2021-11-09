package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
public class AirlineDTO {

    private String name;
    private double currentBalance;
    private LocationDTO homeBaseLocation;

}
