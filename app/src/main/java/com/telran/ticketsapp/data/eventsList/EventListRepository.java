package com.telran.ticketsapp.data.eventsList;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import java.util.List;

import io.reactivex.Single;

public interface EventListRepository {
//    Single<List<EventDto>> getCurrentEvents();
//    Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories);
//    Single<List<EventDto>> getEventsInDateRange(long start, long end);
//    List<Integer> getCategoryFilter();
//    Pair<Long, Long> getDateFilter();

    Single<List<EventDto>> getEvents();

    List<Integer> getCategoryFilter();
    boolean setCategoryFilter(List<Integer> selected);


    Pair<Long, Long> getDateFilter();
    boolean setDateFilter(Pair<Long, Long> dates);

    String getSearchFilter();
    boolean setSearchFilter(String search);
}
