package com.telran.ticketsapp.di.tickets;

import com.telran.ticketsapp.presentation.tickets.presenter.SelectTicketsPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {TicketsModule.class})
@TicketsScope
public interface TicketsComponent {
    void inject(SelectTicketsPresenter presenter);
}
