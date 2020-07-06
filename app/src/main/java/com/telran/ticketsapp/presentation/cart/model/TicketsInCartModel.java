package com.telran.ticketsapp.presentation.cart.model;

import com.telran.ticketsapp.presentation.tickets.model.SeatModel;

import java.util.List;

public class TicketsInCartModel {
    String eventId;
    String eventInfo;
    Double sum;
    List<SeatModel> tickets;

    public TicketsInCartModel(String eventId, String eventInfo, Double sum, List<SeatModel> tickets) {
        this.eventId = eventId;
        this.eventInfo = eventInfo;
        this.sum = sum;
        this.tickets = tickets;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public List<SeatModel> getTickets() {
        return tickets;
    }

    public void setTickets(List<SeatModel> tickets) {
        this.tickets = tickets;
    }
}
