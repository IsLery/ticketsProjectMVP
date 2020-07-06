package com.telran.ticketsapp.di.cart;

import com.telran.ticketsapp.business.cart.CartInteractor;
import com.telran.ticketsapp.business.cart.CartInteractorImpl;
import com.telran.ticketsapp.data.cart.CartRepository;
import com.telran.ticketsapp.data.cart.CartRepositoryImpl;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import dagger.Module;
import dagger.Provides;

@Module
public class CartModule {

    @Provides
    @CartScope
    CartRepository providesCartRepository(TicketsApi api, StoreProvider provider){
        return new CartRepositoryImpl(api,provider);
    }

    @Provides
    @CartScope
    CartInteractor providesCartInteractor(CartRepository repository){
        return new CartInteractorImpl(repository);
    }


}
