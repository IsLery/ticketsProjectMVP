package com.telran.ticketsapp.presentation.login.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface LoginView extends MvpView {
    void showProgress();
    void hideProgress();
    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);
    void showNextView();
    void showPrevView();
    @StateStrategyType(SingleStateStrategy.class)
    void hideErrorDialog();
    void showRecoveryDialog();
}

