package com.telran.ticketsapp.di.tickets;

import com.telran.ticketsapp.business.tickets.SelectTicketsInteractor;
import com.telran.ticketsapp.business.tickets.SelectTicketsInteractorImpl;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.tickets.SelectTicketsRepository;
import com.telran.ticketsapp.data.tickets.SelectTicketsRepositoryImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class TicketsModule {

    @Provides
    @TicketsScope
    SelectTicketsRepository providesSelectTicketsRepository(TicketsApi api, StoreProvider provider){
        return new SelectTicketsRepositoryImpl(api, provider);
    }

    @Provides
    @TicketsScope
    SelectTicketsInteractor providesSelectTicketsInteractor(SelectTicketsRepository repository){
        return new SelectTicketsInteractorImpl(repository);
    }
}
