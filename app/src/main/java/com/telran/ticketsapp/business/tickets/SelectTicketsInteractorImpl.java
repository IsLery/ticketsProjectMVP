package com.telran.ticketsapp.business.tickets;

import com.telran.ticketsapp.data.tickets.SelectTicketsRepository;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;

import io.reactivex.Single;

public class SelectTicketsInteractorImpl implements SelectTicketsInteractor {
    SelectTicketsRepository repository;

    public SelectTicketsInteractorImpl(SelectTicketsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<HallStructureDto> getTicketsInfo(String eventId) {
        return repository.getTicketsInfo(eventId, false);
    }
}
