package com.justedlev.service.airlines.service.exceptions;

public class AirlineNotExistsException extends NotExistsException {
    public AirlineNotExistsException(String additional) {
        super(String.format("Airline %s not exists", additional));
    }
}
