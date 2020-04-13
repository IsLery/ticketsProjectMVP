package com.telran.ticketsapp.presentation.eventList.view;

import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.List;

public interface EventAdapterLogic {
    void updateAllEvents(List<EventDto> newList);
    void addMoreEvents(List<EventDto> moreEvents);
}
