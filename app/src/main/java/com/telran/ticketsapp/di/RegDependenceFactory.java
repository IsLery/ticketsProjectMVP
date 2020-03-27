package com.telran.ticketsapp.di;

import com.telran.ticketsapp.business.registration.RegInteractor;
import com.telran.ticketsapp.business.registration.RegInteractorImpl;
import com.telran.ticketsapp.data.registration.RegRepoImpl;
import com.telran.ticketsapp.data.registration.RegRepository;

public class RegDependenceFactory {
    private static RegDependenceFactory instance = new RegDependenceFactory();
    private RegInteractor interactor;
    private RegRepository repository;

    private RegDependenceFactory(){
        repository = new RegRepoImpl(TicketsApiProvider.getInstance().getApi());
        interactor = new RegInteractorImpl(repository);
    }

    public static RegDependenceFactory getInstance() {
        return instance;
    }

    public RegInteractor getInteractor() {
        return interactor;
    }
}
