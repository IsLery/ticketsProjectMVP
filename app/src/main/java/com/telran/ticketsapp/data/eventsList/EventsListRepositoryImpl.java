package com.telran.ticketsapp.data.eventsList;

import android.util.Log;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.dto.EventDateHallDto;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import io.reactivex.Single;
import retrofit2.Response;

public class EventsListRepositoryImpl implements EventListRepository{
    private static final String TAG = "EventsListRepoImpl";

    //API
    private TicketsApi api;
    private int pageNumber;
    private int pageSize;
    private boolean isLastPage;

    //switcher between 2 methods - current or by date, by default gets current events
    private boolean gettingCurrentEvents;
    private List<EventDto> allEvents;


    //Category filters array - if empty -> all categs
    private List<Integer> categFilters;
    private boolean categFilterChanged;

    //in case dates in date range were changed
    private Pair<Long, Long> dateFilter;

    //searchFilter
    private String search;
    private boolean searchFilterChanged;
    private boolean dateFilterChanged;


    public EventsListRepositoryImpl(TicketsApi api) {

        this.api = api;
        categFilters = new ArrayList<>();
        allEvents = new ArrayList<>();
        search = "";
        gettingCurrentEvents = true;
        pageNumber = 0;
        pageSize = 4;
    }

    @Override
    public Single<List<EventDto>> getEvents() {
        if ((categFilterChanged || searchFilterChanged) && !dateFilterChanged ){
            categFilterChanged = false;
            searchFilterChanged = false;
            Log.d(TAG, "getEvents: return filtered list");
            return Single.just(allEvents).flatMap(this::getFilteredByCategory).flatMap(this::getSearchedList);
        }
        if (dateFilter == null || dateFilter.first == null || dateFilter.second == null){
                return getCurrentEvents();
        }
        return getEventsInDateRange();
    }

    private Single<List<EventDto>> getSearchedList(List<EventDto> list) {
        if (search == null || search.isEmpty()){
            return Single.just(list);
        }
        List<EventDto> filtered = new ArrayList<>();
        for (EventDto e: list
             ) {
            if (e.searchInString().contains(search.toLowerCase())){
                filtered.add(e);
            }
        }
        return Single.just(filtered);
    }

    @Override
    public List<Integer> getCategoryFilter() {
        return categFilters;
    }

    @Override
    public boolean setCategoryFilter(List<Integer> selected) {
        if (selected == null){
            if (categFilters.size() > 0){
                categFilterChanged = true;
                categFilters.clear();
                return true;
            }
            return false;
        }else  if (categFilters.size() != selected.size() || !categFilters.containsAll(selected)){
            categFilters = selected;
            categFilterChanged = true;
            return true;
        }
        return false;
    }

    @Override
    public Pair<Long, Long> getDateFilter() {
        return dateFilter;
    }

    @Override
    public boolean setDateFilter(Pair<Long, Long> dates) {
        if (dates == null && dateFilter == null){
            return false;
        }
        if (dateFilter == null || dates == null || !Objects.equals(dateFilter.first, dates.first) || !Objects.equals(dateFilter.second, dates.second)){
            dateFilter = dates;
            dateFilterChanged = true;
            Log.d(TAG, "setDateFilter: "+dateFilter);
            return true;
        }
        return false;
    }

    @Override
    public String getSearchFilter() {
        return search;
    }

    @Override
    public boolean setSearchFilter(String searchNew) {
        Log.d(TAG, "setSearchFilter: new search "+searchNew + " old " + search);
        if ((searchNew.trim().isEmpty()) && (search.isEmpty())){
            Log.d(TAG, "setSearchFilter: no changes empty"+searchFilterChanged);
            return false;
        }
        if (!search.equals(searchNew.trim())){
            search = searchNew;
            searchFilterChanged = true;
            Log.d(TAG, "setSearchFilter: changes"+searchFilterChanged);
            return true;
        }
        Log.d(TAG, "setSearchFilter: no changes"+searchFilterChanged);
        return false;
    }



    public Single<List<EventDto>> getCurrentEvents() {
        if (!gettingCurrentEvents){
            gettingCurrentEvents = true;
            resetPagination();
        }
        Log.d(TAG, "getCurrentEvents: start");
        if (isLastPage){
            return Single.error(new EndOfListException());
        }
        return api.getCurrentEvents(pageNumber,pageSize).flatMap(this::onGetEventsSuccess);
    }


    public Single<List<EventDto>> getEventsInDateRange() {
        if (gettingCurrentEvents || dateFilterChanged){
            gettingCurrentEvents = false;
            dateFilterChanged = false;
            resetPagination();
        }
        EventDateHallDto dto = new EventDateHallDto(dateFilter.first,dateFilter.second);
        Log.d(TAG, "getEventsInDateRange: start");
        if (isLastPage){
            return Single.error(new EndOfListException());
        }
        return api.getByDates(pageNumber,pageSize,dto).flatMap(this::onGetEventsSuccess);
    }
//
//    @Override
//    public List<Integer> getCategoryFilter() {
//        return categFilters;
//    }
//
//    @Override
//    public Pair<Long, Long> getDateFilter() {
//        return new Pair<>(startDate,endDate);
//    }
//
//
    //clears list and pagination
    private void resetPagination() {
        Log.d(TAG, "resetPagination: ");
        pageNumber = 0;
        isLastPage = false;
        allEvents.clear();
    }
//
//    @Override
//    public Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories) {
//        if (selectedCategories== null || selectedCategories.isEmpty()){
//            filtersApplied = false;
//            categFilters.clear();
//            return Single.just(allEvents);
//        }
//
//        Log.d(TAG, "getFilteredByCategory: categs"+selectedCategories.size());
//        Log.d(TAG, "getFilteredByCategory: events all"+allEvents.size());
//
//        filtersApplied = true;
//        categFilters = selectedCategories;
//        return getFilteredList(allEvents);
//    }
//
//
//
    private Single<List<EventDto>> getFilteredByCategory(List<EventDto> listToFilter) {
        if (categFilters.size() == 0){
            return Single.just(listToFilter);
        }
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
            return Single.just(eventsToAdd).flatMap(this::getFilteredByCategory).flatMap(this::getSearchedList);
        }else {
            Log.d(TAG, "onGetEventsSuccess: "+ listResponse.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


}
