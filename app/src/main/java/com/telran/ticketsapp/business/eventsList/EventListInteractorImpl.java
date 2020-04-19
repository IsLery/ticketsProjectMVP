package com.telran.ticketsapp.business.eventsList;


import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.EventListRepository;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class EventListInteractorImpl implements EventListInteractor {
    public static final String TAG = "EventListIntrImpl";
    EventListRepository repository;

    public EventListInteractorImpl(EventListRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public Single<List<EventDto>> getCurrentEvents() {
//        Log.d(TAG, "getCurrentEvents: start");
//        return repository.getCurrentEvents();
//    }
//
//
//
//    @Override
//    public Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories) {
//        return repository.getFilteredByCategory(selectedCategories);
//    }
//
//    @Override
//    public Single<List<EventDto>> getEventsInDateRange(long start, long end) {
//        return repository.getEventsInDateRange(start, end);
//    }

    @Override
    public Single<List<EventDto>> getEvents() {
        return repository.getEvents();
    }

    @Override
    public List<Integer> getCategoryFilter() {

        List<Integer> categs = repository.getCategoryFilter();
        if (categs.size() == 3) {
            return new ArrayList<>();
        } else return categs;
    }

    @Override
    public boolean setCategoryFilter(List<Integer> selected) {
        if (selected== null || selected.size() == 3){
            return repository.setCategoryFilter(null);
        }
        return repository.setCategoryFilter(selected);
    }

    @Override
    public Pair<Long, Long> getDateFilter() {
        return repository.getDateFilter();
    }

    @Override
    public boolean setDateFilter(long date1, long date2) {
        if (date1 == 0) {
            return repository.setDateFilter(null);
        }
        if (date1 < date2) {
            return repository.setDateFilter(new Pair<>(date1, date2));
        } else if (date1 == date2) {
            return repository.setDateFilter(new Pair<>(date1, date2 + TimeUnit.HOURS.toMillis(24) - 1));
        } else {
            return repository.setDateFilter(new Pair<>(date2, date1));
        }
    }

    @Override
    public String getSearchFilter() {
        return repository.getSearchFilter();
    }

    @Override
    public boolean setSearchFilter(String query) {
        return repository.setSearchFilter(query);

    }


}
