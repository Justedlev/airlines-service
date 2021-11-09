package com.justedlev.service.airlines.service.exceptions;

public class AirlineAlreadyExistsException extends AlreadyExistsException {
    public AirlineAlreadyExistsException(String additional) {
        super(String.format("Airline %s already exists", additional));
    }
}
