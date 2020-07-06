package com.telran.ticketsapp.data.cart;

import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CartRepository {
    Single<TicketsInCartModel> getCartInfo();
    Completable clearCart();
}
