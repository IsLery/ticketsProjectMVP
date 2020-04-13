package com.telran.ticketsapp.business.eventPage;

import com.telran.ticketsapp.data.eventPage.EventRepository;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import io.reactivex.Single;

public class EventInteractorImpl implements EventInteractor{
    EventRepository repository;

    public EventInteractorImpl(EventRepository repository) {
        this.repository = repository;
    }

    public Single<UIEvent> getEvent(String id){
        return repository.getEvent(id);
    }
}
