package com.telran.ticketsapp.business.tickets;

import android.util.Log;

import com.telran.ticketsapp.data.tickets.SelectTicketsRepository;
import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.LockedSeats;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;
import com.telran.ticketsapp.presentation.tickets.model.SeatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;

public class SelectTicketsInteractorImpl implements SelectTicketsInteractor {
    SelectTicketsRepository repository;
    private static final String TAG = "SelectTicketsInteractor";

    public SelectTicketsInteractorImpl(SelectTicketsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<HallStructureDto> getTicketsInfo(String eventId) {
        return repository.getTicketsInfo(eventId, false);
    }

    @Override
    public Completable bookTickets(String eventId, TicketsInCartModel model, Map<String, List<String>> seats) {
        return repository.bookTickets(eventId, model, seats);

    }
}
