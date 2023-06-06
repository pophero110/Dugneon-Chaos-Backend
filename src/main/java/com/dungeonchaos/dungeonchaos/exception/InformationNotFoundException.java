package com.dungeonchaos.dungeonchaos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when attempting to a record is not found in database
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationNotFoundException extends RuntimeException {
    public InformationNotFoundException(String message) {
        super(message);
    }
}