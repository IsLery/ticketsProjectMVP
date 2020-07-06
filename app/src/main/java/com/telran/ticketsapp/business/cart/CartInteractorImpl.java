package com.telran.ticketsapp.business.cart;

import com.telran.ticketsapp.data.cart.CartRepository;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CartInteractorImpl implements CartInteractor {
    CartRepository repository;

    public CartInteractorImpl(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<TicketsInCartModel> getCartInfo() {
        return repository.getCartInfo();
    }

    @Override
    public Completable clearCart() {
        return repository.clearCart();
    }
}
