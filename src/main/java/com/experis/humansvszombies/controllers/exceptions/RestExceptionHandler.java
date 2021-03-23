package com.experis.humansvszombies.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorWrapper> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<ErrorWrapper> buildResponseEntity(ApiError apiError) {
        ErrorWrapper errorWrapper = new ErrorWrapper(apiError);
        return new ResponseEntity<>(errorWrapper, apiError.getStatus());
    }
}
