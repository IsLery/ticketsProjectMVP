package com.telran.ticketsapp.business.tickets;

import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;
import com.telran.ticketsapp.presentation.tickets.model.SeatModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SelectTicketsInteractor {
    Single<HallStructureDto> getTicketsInfo(String eventId);
    Completable bookTickets(String eventId, TicketsInCartModel model, Map<String, List<String>> seats);
}
