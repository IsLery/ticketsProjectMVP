package com.telran.ticketsapp.data.login.models;

public class PasswordRecoveryDto {
    String email;

    public PasswordRecoveryDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
