package com.telran.ticketsapp.di.login;

import com.telran.ticketsapp.business.login.LoginInteractor;
import com.telran.ticketsapp.business.login.LoginInteractorImpl;
import com.telran.ticketsapp.data.login.LoginRepoImpl;
import com.telran.ticketsapp.data.login.LoginRepository;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    @LoginScope
    LoginRepository providesLoginRepository(TicketsApi api, StoreProvider provider){
        return new LoginRepoImpl(api,provider);
    }

    @Provides
    @LoginScope
    LoginInteractor providesLoginInteractor(LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }

}
