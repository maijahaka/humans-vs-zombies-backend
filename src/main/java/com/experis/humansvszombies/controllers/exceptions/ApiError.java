package com.experis.humansvszombies.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.springframework.http.HttpStatus;
/*
* Class modeling an API error. Constructed in RestExceptionHandler when a service throws an error.
* API error has the response code of the request and a custom message attached to it.
*
* API error json response is wrapped as an "error" object.
 */
@JsonTypeName("error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
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

