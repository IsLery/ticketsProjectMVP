package com.telran.ticketsapp.business.cart;

import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CartInteractor {
    Single<TicketsInCartModel> getCartInfo();
    Completable clearCart();
}
