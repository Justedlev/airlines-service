package com.justedlev.service.airlines.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String exception) {
        super(exception);
    }
}
