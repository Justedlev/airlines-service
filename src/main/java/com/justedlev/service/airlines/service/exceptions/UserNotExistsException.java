package com.justedlev.service.airlines.service.exceptions;

public class UserNotExistsException extends NotExistsException {
    public UserNotExistsException(String additional) {
        super(String.format("User %s not exists", additional));
    }
}
