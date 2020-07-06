package com.telran.ticketsapp.presentation.eventpage.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EventView extends MvpView {

    void showProgress();
    void hideProgress();
    @StateStrategyType(SkipStrategy.class)
    void showNextView(UIEvent event);
    void showError(String error);
    void setEventInfo(UIEvent event);
}
