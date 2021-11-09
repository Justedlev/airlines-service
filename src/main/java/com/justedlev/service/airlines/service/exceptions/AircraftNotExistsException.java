package com.justedlev.service.airlines.service.exceptions;

public class AircraftNotExistsException extends NotExistsException {
    public AircraftNotExistsException(String additional) {
        super(String.format("Aircraft %s not exists", additional));
    }
}
