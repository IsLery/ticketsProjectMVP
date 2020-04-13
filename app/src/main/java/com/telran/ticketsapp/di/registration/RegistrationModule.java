package com.telran.ticketsapp.di.registration;

import com.telran.ticketsapp.business.registration.RegInteractor;
import com.telran.ticketsapp.business.registration.RegInteractorImpl;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.registration.RegRepoImpl;
import com.telran.ticketsapp.data.registration.RegRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RegistrationModule {

    @Provides
    @RegistrationScope
    RegRepository providesRegRepositpry(TicketsApi api){
        return new RegRepoImpl(api);
    }

    @Provides
    @RegistrationScope
    RegInteractor providesRegInteractor(RegRepository repository){
        return new RegInteractorImpl(repository);
    }




}
