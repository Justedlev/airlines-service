package com.justedlev.service.airlines.api.dto;

import lombok.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Data
@AllArgsConstructor
public class DestinationDTO {

    private String name;
    private LocationDTO location;

}
