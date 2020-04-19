package com.telran.ticketsapp.data.tickets.dto;

import java.util.ArrayList;
import java.util.List;

public class PriceRangeDto {
    double price;
    String color;
    List<String> rows;
    List<Integer> parsedRows;


    public PriceRangeDto(double price, String color, List<String> rows) {
        this.price = price;
        this.color = color;
        this.rows = rows;
    }

    public PriceRangeDto() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }

    private void setParsedRows(){
        parsedRows = new ArrayList<>();
        for (String s: rows) {
            parsedRows.add(Integer.parseInt(s));
        }
    }

    public List<Integer> getParsedRows(){
        if (parsedRows == null){
            setParsedRows();
        }
        return parsedRows;
    }


}
