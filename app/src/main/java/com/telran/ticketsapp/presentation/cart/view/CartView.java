package com.telran.ticketsapp.presentation.cart.view;

import android.os.Bundle;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CartView extends MvpView {
    void clearCart();
    void setData(String eventInfo, double sum, CartAdapter adapter);
    @StateStrategyType(SkipStrategy.class)
    void showNextView(Bundle bundle);

}
