package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
public class LocationDTO {

    private double latitude;
    private double longitude;

}
