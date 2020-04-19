package com.telran.ticketsapp.data.tickets.dto;

import java.util.List;

public class LockedSeats {
    String row;
    List<String> seats;

    public LockedSeats(String row, List<String> seats) {
        this.row = row;
        this.seats = seats;
    }

    public LockedSeats() {
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public List<String> getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        this.seats = seats;
    }

}
