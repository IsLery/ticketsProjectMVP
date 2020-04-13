package com.telran.ticketsapp.di.login;

import com.telran.ticketsapp.presentation.login.presenter.LoginPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = {LoginModule.class})
@LoginScope
public interface LoginComponent {

    void inject (LoginPresenter presenter);
}
