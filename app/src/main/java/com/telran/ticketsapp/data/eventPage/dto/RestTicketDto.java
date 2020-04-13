package com.telran.ticketsapp.data.eventPage.dto;

public class RestTicketDto {
    public String eventId;
    public double maxPrice;
    public double minPrice;
    public int restTick;

    public RestTicketDto(String eventId, double maxPrice, double minPrice, int restTick) {
        this.eventId = eventId;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.restTick = restTick;
    }

    public RestTicketDto() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public int getRestTick() {
        return restTick;
    }

    public void setRestTick(int restTick) {
        this.restTick = restTick;
    }
}
