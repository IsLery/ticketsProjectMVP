package com.telran.ticketsapp;

import android.app.Application;

import com.telran.ticketsapp.di.app.AppComponent;
import com.telran.ticketsapp.di.app.AppModule;
import com.telran.ticketsapp.di.app.DaggerAppComponent;
import com.telran.ticketsapp.di.cart.CartComponent;
import com.telran.ticketsapp.di.cart.CartModule;
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

public class App extends Application {
    public static App app;

    private AppComponent appComponent;
    private LoginComponent loginComponent;
    private RegistrationComponent registrationComponent;
    private EventsListComponent eventsListComponent;
    private EventComponent eventComponent;
    private TicketsComponent ticketsComponent;
    private CartComponent cartComponent;

    public App() {
        app = this;
    }

    public static App get(){
        return app;
    }

    @Override
    public void onCreate() {

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
            .build();
        super.onCreate();
    }

    public LoginComponent plus(LoginModule module){
        if (loginComponent == null){
            loginComponent = appComponent.plus(module);
        }
        return loginComponent;
    }

    public void clearLoginComponent(){
        loginComponent = null;
    }

    public RegistrationComponent plus(RegistrationModule module){
        if (registrationComponent == null){
            registrationComponent = appComponent.plus(module);
        }
        return registrationComponent;
    }

    public void clearRegistrationComponent(){
        registrationComponent = null;
    }

    public EventsListComponent plus(EventsListModule module){
        if (eventsListComponent == null){
            eventsListComponent = appComponent.plus(module);
        }
        return eventsListComponent;
    }

    public void clearEventsListComponent(){
        eventsListComponent = null;
    }

    public EventComponent plus(EventModule module){
        if (eventComponent == null){
            eventComponent = appComponent.plus(module);
        }
        return eventComponent;
    }

    public void clearEventComponent(){
        eventComponent = null;
    }

    public TicketsComponent plus(TicketsModule module){
        if (ticketsComponent == null){
            ticketsComponent = appComponent.plus(module);
        }
        return ticketsComponent;
    }

    public void clearTicketsComponent(){
        ticketsComponent = null;
    }

    public CartComponent plus(CartModule module){
        if (cartComponent == null){
            cartComponent = appComponent.plus(module);
        }
        return cartComponent;
    }

    public void clearCartComponent(){
        cartComponent = null;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
