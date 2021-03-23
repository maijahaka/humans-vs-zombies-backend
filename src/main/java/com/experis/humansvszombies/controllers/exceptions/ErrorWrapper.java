package com.experis.humansvszombies.controllers.exceptions;

public class ErrorWrapper {
    private Object error;

    public Object getError() {
        return error;
    }
    public void setError(Object error) {
        this.error = error;
    }

    public ErrorWrapper(Object error){
        this.error = error;
    }
}
