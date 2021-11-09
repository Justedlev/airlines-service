package com.justedlev.service.airlines.service.exceptions;

public class UserAlreadyExistsException extends AlreadyExistsException {
    public UserAlreadyExistsException(String additional) {
        super(String.format("User %s already exists", additional));
    }
}
