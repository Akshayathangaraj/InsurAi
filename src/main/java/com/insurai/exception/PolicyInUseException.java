package com.insurai.insurai.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PolicyInUseException extends RuntimeException {
    public PolicyInUseException(String message) {
        super(message);
    }
}
