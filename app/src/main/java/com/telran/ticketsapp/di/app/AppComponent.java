package com.telran.ticketsapp.di.app;

import com.telran.ticketsapp.di.eventPage.EventComponent;
import com.telran.ticketsapp.di.eventPage.EventModule;
import com.telran.ticketsapp.di.eventsList.EventsListComponent;
import com.telran.ticketsapp.di.eventsList.EventsListModule;
import com.telran.ticketsapp.di.login.LoginComponent;
import com.telran.ticketsapp.di.login.LoginModule;
import com.telran.ticketsapp.di.registration.RegistrationComponent;
import com.telran.ticketsapp.di.registration.RegistrationModule;
import com.telran.ticketsapp.di.tickets.TicketsComponent;
import com.telran.ticketsapp.di.tickets.TicketsModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    LoginComponent plus(LoginModule module);
    RegistrationComponent plus(RegistrationModule module);
    EventsListComponent plus(EventsListModule module);
    EventComponent plus(EventModule module);
    TicketsComponent plus(TicketsModule module);
}
