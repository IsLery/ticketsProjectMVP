package com.telran.ticketsapp.data.eventsList.dto;

import java.util.HashMap;
import java.util.List;

public class PriceRanges {
    HashMap<String,List<String>> seats;
    double price;
    String color;

    public HashMap<String, List<String>> getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }
}
