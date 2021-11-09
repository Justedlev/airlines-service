package com.justedlev.service.airlines.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotExistsException extends RuntimeException {
    public NotExistsException(String exception) {
        super(exception);
    }
}
