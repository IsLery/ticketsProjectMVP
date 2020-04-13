package com.telran.ticketsapp.data.provider.store;

import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.List;

public interface EventsStoreProvider {
    void saveEvents(List<EventDto> events);
    List<EventDto> getEvents();
}
