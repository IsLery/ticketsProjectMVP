package com.telran.ticketsapp.data.eventsList.models;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EventDto {
    public String eventId;
    public int eventStatus;
    public String eventName;
    public String artist;
    public long eventStart; // $int64
    public int eventDurationHours;
    public int hall; //hallId
    public int eventType;
    public String description;
    public List<String> images;
    public Set<PriceRanges> priceRanges; // TODO create priceranges class, it it really a set?
    public Set<String> managers;



    @Override
    public String toString() {
        return "EventOutputDto{" +
                "eventId='" + eventId + '\'' +
                ", eventStatus=" + eventStatus +
                ", eventName='" + eventName + '\'' +
                ", artist='" + artist + '\'' +
                ", eventStart=" + eventStart +
                ", eventDurationHours=" + eventDurationHours +
                ", hall=" + hall +
                ", eventType=" + eventType +
                ", description='" + description + '\'' +
                ", images=" + images +
                ", priceRanges=" + priceRanges +
                ", managers=" + managers +
                '}';
    }

    public String getEventId() {
        return eventId;
    }

    public int getEventStatus() {
        return eventStatus;
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

    public int getEventDurationHours() {
        return eventDurationHours;
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

    public Set<PriceRanges> getPriceRanges() {
        return priceRanges;
    }

    public Set<String> getManagers() {
        return managers;
    }

    public String getDateTxt() {
        Date date = new Date();
        date.setTime(eventStart);
        SimpleDateFormat format = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        format.applyPattern("EEE, MMM d, yyyy");
        return format.format(date);
    }
}
