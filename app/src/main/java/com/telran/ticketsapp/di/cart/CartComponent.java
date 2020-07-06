package com.telran.ticketsapp.di.cart;


import com.telran.ticketsapp.presentation.cart.presenter.CartPresenter;

import dagger.Subcomponent;

@CartScope
@Subcomponent(modules = {CartModule.class})
public interface CartComponent {
    public void inject(CartPresenter presenter);
}
