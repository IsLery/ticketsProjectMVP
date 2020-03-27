package com.telran.ticketsapp.business.eventsList;

import android.util.Log;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.EventListRepository;
import com.telran.ticketsapp.data.eventsList.models.EventDto;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class EventListInteractorImpl implements EventListInteractor{
    public static final String TAG = "EventListIntrImpl";
    EventListRepository repository;

    public EventListInteractorImpl(EventListRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<EventDto>> getCurrentEvents() {
        Log.d(TAG, "getCurrentEvents: start");
        return repository.getCurrentEvents();
    }



    @Override
    public Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories) {
        return repository.getFilteredByCategory(selectedCategories);
    }

    @Override
    public Single<List<EventDto>> getEventsInDateRange(long start, long end) {
        return repository.getEventsInDateRange(start, end);
    }

    @Override
    public List<Integer> getCategoryFilter() {
        return repository.getCategoryFilter();
    }

    @Override
    public Pair<Long, Long> getDateFilter() {
        return repository.getDateFilter();
    }


}
