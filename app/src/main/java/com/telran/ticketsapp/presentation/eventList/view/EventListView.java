package com.telran.ticketsapp.presentation.eventList.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


import java.util.ArrayList;
import java.util.List;
@StateStrategyType(AddToEndSingleStrategy.class)
public interface EventListView extends MvpView {
    void showProgress();
    void hideProgress();
    void showEvent();
    void isLoadingData(boolean stillLoading);
    @StateStrategyType(SingleStateStrategy.class)
    void showError(String m);
   // @StateStrategyType(OneExecutionStateStrategy.class)
    void setScrollListener();
    void removeScrollListener();
    void showFiltersDialog();
  // void setAdapter(EventListAdapter adapter);

}
