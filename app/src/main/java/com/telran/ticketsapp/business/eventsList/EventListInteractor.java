package com.telran.ticketsapp.business.eventsList;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.List;

import io.reactivex.Single;

public interface EventListInteractor {
//    Single<List<EventDto>> getCurrentEvents();
//    Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories);
//    Single<List<EventDto>> getEventsInDateRange(long start, long end);

    //update and load more
    Single<List<EventDto>> getEvents();

    List<Integer> getCategoryFilter();
    boolean setCategoryFilter(List<Integer> selected);


    Pair<Long, Long> getDateFilter();
    boolean setDateFilter(long date1, long date2);

    String getSearchFilter();
    boolean setSearchFilter(String query);
}
