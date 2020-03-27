package com.telran.ticketsapp.presentation.eventList.view;

import com.arellomobile.mvp.MvpView;
import com.telran.ticketsapp.data.eventsList.models.EventDto;

import java.util.List;

public interface EventListView extends MvpView {
    void showProgress();
    void hideProgress();
    void showEvent();
    void updateAll(List<EventDto> eventDtos);
    void loadMoreData(List<EventDto> eventDtos);
    void showError(String m);

}
