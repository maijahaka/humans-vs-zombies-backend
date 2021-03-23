package com.experis.humansvszombies.models.wrappers;
import org.springframework.http.HttpStatus;

public class  ResponseWrapper {
    private HttpStatus httpStatus;
    private Object data;

    public ResponseWrapper(){

    }

    public ResponseWrapper(Object data, HttpStatus httpStatus){
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
