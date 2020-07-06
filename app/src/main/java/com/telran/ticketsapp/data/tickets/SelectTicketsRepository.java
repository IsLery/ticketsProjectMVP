package com.telran.ticketsapp.data.tickets;

import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;


import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SelectTicketsRepository {
    Single<HallStructureDto> getTicketsInfo(String eventId, boolean isShort);

    Completable bookTickets(String eventId, TicketsInCartModel model, Map<String, List<String>> seats);
}
