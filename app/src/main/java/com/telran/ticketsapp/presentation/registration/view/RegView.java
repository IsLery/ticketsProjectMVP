package com.telran.ticketsapp.presentation.registration.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RegView extends MvpView {
    String TEST = "test";
    void showProgress();
    void hideProgress();
    @StateStrategyType(SingleStateStrategy.class)
    void showError(String error);
    @StateStrategyType(SkipStrategy.class)
    void showNextView();
    @StateStrategyType(SingleStateStrategy.class)
    void hideErrorDialog();
}
