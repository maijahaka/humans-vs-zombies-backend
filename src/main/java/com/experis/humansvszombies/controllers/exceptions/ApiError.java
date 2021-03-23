package com.experis.humansvszombies.controllers.exceptions;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

