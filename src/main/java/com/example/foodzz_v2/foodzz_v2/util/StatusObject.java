package com.example.foodzz_v2.foodzz_v2.util;

import java.util.List;

public class StatusObject <T> {

    private int status;
    private String message;
    private List<?> genericListResponse;
    private T genericResponse;

    public StatusObject() {}

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public <T> List<T> getGenericListResponse() {
        return (List<T>) genericListResponse;
    }

    public void setGenericListResponse(List<?> genericListResponse) {
        this.genericListResponse = genericListResponse;
    }

    public T getGenericResponse() {
        return genericResponse;
    }

    public void setGenericResponse(T genericResponse) {
        this.genericResponse = genericResponse;
    }
}
