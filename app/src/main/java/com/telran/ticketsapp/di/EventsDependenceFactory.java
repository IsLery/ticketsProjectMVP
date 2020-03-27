package com.telran.ticketsapp.di;

import android.content.Context;

import com.telran.ticketsapp.business.eventsList.EventListInteractor;
import com.telran.ticketsapp.business.eventsList.EventListInteractorImpl;
import com.telran.ticketsapp.data.eventsList.EventListRepository;
import com.telran.ticketsapp.data.eventsList.EventsListRepositoryImpl;

public class EventsDependenceFactory {
    private static EventsDependenceFactory instance;
    private EventListRepository repository;
    private EventListInteractor interactor;
    private Context context;

    public EventsDependenceFactory(Context context) {
        repository = new EventsListRepositoryImpl(TicketsApiProvider.getInstance().getApi());
        interactor = new EventListInteractorImpl(repository);
        instance = this;
    }

    public static EventsDependenceFactory getInstance() {
        return instance;
    }

    public EventListInteractor getInteractor() {
        return interactor;
    }

    public Context getContext() {
        return context;
    }
}
