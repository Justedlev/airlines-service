package com.justedlev.service.airlines.service.exceptions;

public class AircraftAlreadyExistsException extends AlreadyExistsException {
    public AircraftAlreadyExistsException(String additional) {
        super(String.format("Aircraft %s already exists", additional));
    }
}
