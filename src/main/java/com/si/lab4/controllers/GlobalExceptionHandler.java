package com.si.lab4.controllers;

import com.si.lab4.exceptions.SomethingIsWrongException;
import com.si.lab4.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(SomethingIsWrongException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ErrorResponse> somethingIsWrong(HttpServletRequest request, SomethingIsWrongException e){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(),
                        "Something is wrong",
                        e.getMessage(),
                        request.getServletPath()));
    }
}
