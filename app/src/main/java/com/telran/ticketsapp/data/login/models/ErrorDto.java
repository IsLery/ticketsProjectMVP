package com.telran.ticketsapp.data.login.models;

public class ErrorDto {
    String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
