package com.telran.ticketsapp.business.registration;

public class InvalidFieldsException extends RuntimeException{
    public InvalidFieldsException(String message) {
        super(message);
    }
}
