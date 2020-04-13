package com.telran.ticketsapp.presentation.eventList.view.filters;



import androidx.core.util.Pair;

import com.arellomobile.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface EventFiltersView{
    void applyFilters();
    void getAppliedCategoryFilters(List<Integer> filters);
    void getAppliedDateFilters(Pair<Long, Long> period);
    void closeDialog();
//    void categoryChecked(long catId);
//    void categoryUnchecked(long catId);

}
