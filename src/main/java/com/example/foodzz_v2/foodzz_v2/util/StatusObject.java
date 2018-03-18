package com.example.foodzz_v2.foodzz_v2.util;

public class StatusObject {

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

    private int status;
    private String message;

    public StatusObject() {
    }
}
