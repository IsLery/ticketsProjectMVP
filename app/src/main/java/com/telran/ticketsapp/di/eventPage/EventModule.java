package com.telran.ticketsapp.di.eventPage;

import com.telran.ticketsapp.business.eventPage.EventInteractor;
import com.telran.ticketsapp.business.eventPage.EventInteractorImpl;
import com.telran.ticketsapp.data.eventPage.EventRepository;
import com.telran.ticketsapp.data.eventPage.EventRepositoryImpl;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import dagger.Module;
import dagger.Provides;

@Module
public class EventModule {
    @Provides
    @EventPageScope
    EventRepository providesEventRepository(TicketsApi api){
        return new EventRepositoryImpl(api);
    }

    @Provides
    @EventPageScope
    EventInteractor providesEventInteractor(EventRepository repository){
        return new EventInteractorImpl(repository);
    }

}
