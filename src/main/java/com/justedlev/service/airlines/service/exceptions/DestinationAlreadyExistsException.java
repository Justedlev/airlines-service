package com.justedlev.service.airlines.service.exceptions;

public class DestinationAlreadyExistsException extends AlreadyExistsException {
    public DestinationAlreadyExistsException(String additional) {
        super(String.format("Destination %s already exists", additional));
    }
}
