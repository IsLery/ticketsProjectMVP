package com.telran.ticketsapp.business.eventsList;

import androidx.core.util.Pair;

import com.telran.ticketsapp.data.eventsList.models.EventDto;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface EventListInteractor {
    Single<List<EventDto>> getCurrentEvents();
    Single<List<EventDto>> getFilteredByCategory(ArrayList<Integer> selectedCategories);
    Single<List<EventDto>> getEventsInDateRange(long start, long end);
    List<Integer> getCategoryFilter();
    Pair<Long, Long> getDateFilter();
}
