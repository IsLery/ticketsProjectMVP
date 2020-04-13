package com.telran.ticketsapp.presentation.eventpage.view;

import com.arellomobile.mvp.MvpView;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;


public interface EventView extends MvpView {

    void showProgress();
    void hideProgress();
    void showNextView();
    void showError(String error);
    void setEventInfo(UIEvent event);
}
