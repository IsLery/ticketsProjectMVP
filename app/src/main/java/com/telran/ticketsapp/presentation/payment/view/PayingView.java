package com.telran.ticketsapp.presentation.payment.view;

import android.os.Bundle;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PayingView extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void unblockPayBtn(boolean isEnabled);
    @StateStrategyType(SkipStrategy.class)
    void showNextView(Bundle bundle);
    void showSuccess();
}
