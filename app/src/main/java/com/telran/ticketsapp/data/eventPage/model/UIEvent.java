package com.telran.ticketsapp.data.eventPage.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UIEvent {
     String eventId;
     String eventName;
     String artist;
     long eventStart;
     int hall;
     int eventType;
     String description;
     List<String> images;
    double maxPrice;
    double minPrice;
    int restTick;

    public UIEvent(String eventId, String eventName, String artist, long eventStart, int hall, int eventType, String description, List<String> images, double maxPrice, double minPrice, int restTick) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.artist = artist;
        this.eventStart = eventStart;
        this.hall = hall;
        this.eventType = eventType;
        this.description = description;
        this.images = images;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.restTick = restTick;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getArtist() {
        return artist;
    }

    public long getEventStart() {
        return eventStart;
    }

    public int getHall() {
        return hall;
    }

    public int getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public int getRestTick() {
        return restTick;
    }
}
