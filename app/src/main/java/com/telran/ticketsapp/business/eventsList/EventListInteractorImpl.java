package com.telran.ticketsapp.business.eventsList;


import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.EventListRepository;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class EventListInteractorImpl implements EventListInteractor{
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
        if (categs.size() == 3){
             return new ArrayList<>();
        }
        else return categs;
    }

    @Override
    public boolean setCategoryFilter(List<Integer> selected) {
       return repository.setCategoryFilter(selected);
    }

    @Override
    public Pair<Long, Long> getDateFilter() {
        return repository.getDateFilter();
    }

    @Override
    public boolean setDateFilter(Long date1, Long date2) {
        if (date1 != null && date2 != null){
            if (date1 < date2){
            return     repository.setDateFilter(new Pair<>(date1, date2));
            }else if (date1.equals(date2)){
              return   repository.setDateFilter(new Pair<>(date1, date2+ TimeUnit.HOURS.toMillis(24)-1));
            }else {
              return   repository.setDateFilter(new Pair<>(date2, date1));
            }
        }else {
          return   repository.setDateFilter(null);
        }
    }

    @Override
    public String getSearchFilter() {
        return repository.getSearchFilter();
    }

    @Override
    public boolean setSearchFilter(String query) {
      return   repository.setSearchFilter(query);

    }


}
