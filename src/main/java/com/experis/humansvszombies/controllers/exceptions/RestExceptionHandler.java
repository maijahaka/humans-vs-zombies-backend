package com.experis.humansvszombies.controllers.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
/*
* Exception handler for all thrown API errors. Constructs a new APIError object and returns
* it as an response entity. APIError contains the exception message and HTTP response code of the thrown exception.
 */
@ControllerAdvice
public class RestExceptionHandler{
    //catches the ResponseStatusExceptions thrown by services
    @ExceptionHandler
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        ApiError apiError = new ApiError(ex.getStatus(), ex.getReason());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
