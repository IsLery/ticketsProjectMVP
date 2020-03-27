package com.telran.ticketsapp.data.provider.store;

import android.content.Context;

import com.telran.ticketsapp.data.eventsList.models.EventDto;

import java.util.List;

public class EventsSharPrefsStoreProvider implements EventsStoreProvider {
    public static final String EVENTS = "all_events";
    public static final String LIST = "events_list";
    Context context;

    public EventsSharPrefsStoreProvider(Context context) {
        this.context = context;
    }

    @Override
    public void saveEvents(List<EventDto> events) {

    }

    @Override
    public List<EventDto> getEvents() {
        return null;
    }


}
