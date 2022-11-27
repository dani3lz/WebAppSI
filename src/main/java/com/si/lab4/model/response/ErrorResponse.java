package com.si.lab4.model.response;

import lombok.Builder;

@Builder
public class ErrorResponse {

    int status;
    String error;
    String message;
    String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
