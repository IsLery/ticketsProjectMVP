package com.telran.ticketsapp.di.eventsList;

import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {EventsListModule.class})
@EventsListScope
public interface EventsListComponent {
    void inject(EventListPresenter presenter);
}
