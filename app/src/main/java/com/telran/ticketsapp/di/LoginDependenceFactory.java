package com.telran.ticketsapp.di;

import android.content.Context;

import com.telran.ticketsapp.business.login.LoginInteractor;
import com.telran.ticketsapp.business.login.LoginInteractorImpl;
import com.telran.ticketsapp.data.login.LoginRepoImpl;
import com.telran.ticketsapp.data.login.LoginRepository;
import com.telran.ticketsapp.data.provider.store.SharPrefsStoreProvider;
import com.telran.ticketsapp.data.provider.store.StoreProvidder;

public class LoginDependenceFactory {
    private static LoginDependenceFactory instance;
    LoginInteractor interactor;
    LoginRepository repository;
    StoreProvidder providder;


    public LoginDependenceFactory(Context context) {
        providder = new SharPrefsStoreProvider(context);
        repository = new LoginRepoImpl(TicketsApiProvider.getInstance().getApi(),providder);
        interactor = new LoginInteractorImpl(repository);
        instance = this;
    }

    public static LoginDependenceFactory getInstance() {
        return instance;
    }

    public LoginInteractor getInteractor() {
        return interactor;
    }
}
