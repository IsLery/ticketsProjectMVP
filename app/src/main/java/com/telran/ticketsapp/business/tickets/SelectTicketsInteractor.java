package com.telran.ticketsapp.business.tickets;

import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;

import io.reactivex.Single;

public interface SelectTicketsInteractor {
    Single<HallStructureDto> getTicketsInfo(String eventId);
}
