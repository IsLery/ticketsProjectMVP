package com.telran.ticketsapp.data.tickets.dto;

import java.util.List;

public class EventBookingDto {
    String eventId;
    List<LockedSeats> lockedSeats;

    public EventBookingDto(String eventId, List<LockedSeats> lockedSeats) {
        this.eventId = eventId;
        this.lockedSeats = lockedSeats;
    }

    public EventBookingDto() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<LockedSeats> getLockedSeats() {
        return lockedSeats;
    }

    public void setLockedSeats(List<LockedSeats> lockedSeats) {
        this.lockedSeats = lockedSeats;
    }


}
