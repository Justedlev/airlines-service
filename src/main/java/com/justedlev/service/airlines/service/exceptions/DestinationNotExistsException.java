package com.justedlev.service.airlines.service.exceptions;

public class DestinationNotExistsException extends NotExistsException {
    public DestinationNotExistsException(String additional) {
        super(String.format("Destination %s not exists", additional));
    }
}
