package com.telran.ticketsapp.data.eventsList;

import android.util.Log;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.models.EventDateHallDto;
import com.telran.ticketsapp.data.eventsList.models.EventDto;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.Single;
import retrofit2.Response;

public class EventsListRepositoryImpl implements EventListRepository{
    private static final String TAG = "EventsListRepoImpl";
    private TicketsApi api;
    private int pageNumber = 1;
    private int pageSize = 3;
    private boolean isLastPage = false;

    //switcher between 2 methods - current or by date, by default gets current events
    private boolean gettingCurrentEvents = true;
    private List<EventDto> allEvents = new ArrayList<>();
    private boolean filtersApplied = false;
    private ArrayList<Integer> categFilters = new ArrayList<>();

    //in case dates in date range were changed
    private long startDate, endDate;


    public EventsListRepositoryImpl(TicketsApi api) {
        this.api = api;
    }

    @Override
    public Single<List<EventDto>> getCurrentEvents() {
        if (!gettingCurrentEvents){
            gettingCurrentEvents = true;
            switchGetMethod();
        }
        Log.d(TAG, "getCurrentEvents: start");
        if (isLastPage){
            return Single.error(new EndOfListException());
        }
        return api.getCurrentEvents(pageNumber,pageSize).flatMap(this::onGetEventsSuccess);
    }

    @Override
    public Single<List<EventDto>> getEventsInDateRange(long start, long end) {
        if (start == 0 && end == 0 && startDate == 0 && endDate == 0){
            return getFilteredList(allEvents);
        }
        if (start == 0 && end == 0){
            startDate = 0;
            endDate = 0;
            gettingCurrentEvents = false;
            return getCurrentEvents();
        }
        EventDateHallDto dto = new EventDateHallDto(start,end);
        if (gettingCurrentEvents || startDate != start || endDate != end){
            gettingCurrentEvents = false;
            switchGetMethod();
        }
        startDate = start;
        endDate = end;
        Log.d(TAG, "getEventsInDateRange: start");
        if (isLastPage){
            return Single.error(new EndOfListException());
        }
        return api.getByDates(pageNumber,pageSize,dto).flatMap(this::onGetEventsSuccess);
    }

    @Override
    public List<Integer> getCategoryFilter() {
        return categFilters;
    }

    @Override
    public Pair<Long, Long> getDateFilter() {
        return new Pair<>(startDate,endDate);
    }


    //clears list and pagination
    private void switchGetMethod() {
        pageNumber = 1;
        isLastPage = false;
        allEvents.clear();
    }

    @Override
    public Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories) {
        if (selectedCategories== null || selectedCategories.isEmpty()){
            filtersApplied = false;
            categFilters.clear();
            return Single.just(allEvents);
        }

        Log.d(TAG, "getFilteredByCategory: categs"+selectedCategories.size());
        Log.d(TAG, "getFilteredByCategory: events all"+allEvents.size());

        filtersApplied = true;
        categFilters = selectedCategories;
        return getFilteredList(allEvents);
    }



    private Single<List<EventDto>> getFilteredList(List<EventDto> listToFilter) {
        List<EventDto> filtered = new ArrayList<>();
        for (EventDto event: listToFilter
             ) {
            if (categFilters.contains(event.getEventType())){
                filtered.add(event);
            }
        }
        return Single.just(filtered);
    }

    private Single<List<EventDto>> onGetEventsSuccess(Response<List<EventDto>> listResponse) throws IOException {

        if (listResponse.isSuccessful()){
            Log.d(TAG, "onGetEventsSuccess: "+listResponse.body());
            List<EventDto> eventsToAdd = listResponse.body();
            if (eventsToAdd == null){
                throw new RuntimeException("Empty list");
            }
            if (eventsToAdd.size() < pageSize){
                isLastPage = true;
            }
            allEvents.addAll(eventsToAdd);
            pageNumber++;
            if (filtersApplied){
                return getFilteredList(eventsToAdd);
            }
            return Single.just(eventsToAdd);
        }else {
            Log.d(TAG, "onGetEventsSuccess: "+ listResponse.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


}
