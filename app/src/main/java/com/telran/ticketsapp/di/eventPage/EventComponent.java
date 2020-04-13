package com.telran.ticketsapp.di.eventPage;

import com.telran.ticketsapp.presentation.eventpage.presenter.EventPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {EventModule.class})
@EventPageScope
public interface EventComponent {
    public void inject(EventPresenter presenter);

}
