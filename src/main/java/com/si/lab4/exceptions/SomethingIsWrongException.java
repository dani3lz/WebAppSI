package com.si.lab4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class SomethingIsWrongException extends RuntimeException {
    public SomethingIsWrongException() {
        super("Something goes wrong...");
    }
}
