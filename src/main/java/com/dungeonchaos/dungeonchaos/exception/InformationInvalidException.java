package com.dungeonchaos.dungeonchaos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InformationInvalidException extends RuntimeException{
    public InformationInvalidException(String message) {
        super(message);
    }
}
