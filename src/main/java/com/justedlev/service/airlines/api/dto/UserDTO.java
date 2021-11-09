package com.justedlev.service.airlines.api.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Data
public class UserDTO {

    private String nickname;
    private String hashCode;
    private Set<String> roles;

}
