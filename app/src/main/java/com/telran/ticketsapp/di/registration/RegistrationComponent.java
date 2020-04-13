package com.telran.ticketsapp.di.registration;

import com.telran.ticketsapp.presentation.registration.presenter.RegistrationPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {RegistrationModule.class})
@RegistrationScope
public interface RegistrationComponent {
    void inject(RegistrationPresenter presenter);
}
