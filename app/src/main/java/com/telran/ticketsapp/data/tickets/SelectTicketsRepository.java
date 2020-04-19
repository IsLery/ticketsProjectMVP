package com.telran.ticketsapp.data.tickets;

import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;


import io.reactivex.Single;

public interface SelectTicketsRepository {
    Single<HallStructureDto> getTicketsInfo(String eventId, boolean isShort);
}
