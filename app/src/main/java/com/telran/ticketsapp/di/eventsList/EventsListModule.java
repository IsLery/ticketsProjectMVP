package com.telran.ticketsapp.di.eventsList;

import com.telran.ticketsapp.business.eventsList.EventListInteractor;
import com.telran.ticketsapp.business.eventsList.EventListInteractorImpl;
import com.telran.ticketsapp.data.eventsList.EventListRepository;
import com.telran.ticketsapp.data.eventsList.EventsListRepositoryImpl;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import dagger.Module;
import dagger.Provides;

@Module
public class EventsListModule {
    @Provides
    @EventsListScope
    EventListRepository providesEventListRepository(TicketsApi api){
        return new EventsListRepositoryImpl(api);
    }

    @Provides
    @EventsListScope
    EventListInteractor providesEventListInteractor(EventListRepository repository){
        return new EventListInteractorImpl(repository);
    }
}
