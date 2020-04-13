package com.telran.ticketsapp.presentation.eventList.presenter;

import android.content.Context;
import android.util.Log;

import androidx.core.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.eventsList.EventListInteractor;
import com.telran.ticketsapp.data.eventsList.EndOfListException;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;

import com.telran.ticketsapp.di.eventsList.EventsListModule;
import com.telran.ticketsapp.presentation.eventList.view.EventAdapterLogic;
import com.telran.ticketsapp.presentation.eventList.view.EventListAdapter;
import com.telran.ticketsapp.presentation.eventList.view.EventListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventListPresenter extends MvpPresenter<EventListView> {
    public static final String TAG = "EventListPresenter";
    private boolean loadingMore = false;
    Disposable disposable;

    @Inject
    Context context;

    @Inject
    EventListInteractor interactor;

    EventAdapterLogic adapterLogic;


    public EventListPresenter() {
        App.get().plus(new EventsListModule()).inject(this);
//        interactor = EventsDependenceFactory.getInstance().getInteractor();
//        context = EventsDependenceFactory.getInstance().getContext();
        Log.d(TAG, "EventListPresenter: " + context);
        adapterLogic = new EventListAdapter(context);

    }

    //TODO переделать через даггер
    public EventListAdapter getAdapterInstance(){
        return (EventListAdapter) adapterLogic;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Log.d(TAG, "onFirstViewAttach: ");
        getViewState().setScrollListener();
        getEvents();
    }


    public void getEvents(){
        Log.d(TAG, "getEvents: start");
         getViewState().showProgress();
        disposable = interactor.getEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateEventsList,throwable ->
                {   if (throwable instanceof EndOfListException){
                    getViewState().removeScrollListener();
                    getViewState().hideProgress();
                    loadingMore = false;
                }else {
                    showError(throwable.getMessage());}});
    }

    public void loadmore(){
        Log.d(TAG, "loadmore: ");
        loadingMore = true;
        getEvents();
    }


    private void showError(String message) {
        getViewState().hideProgress();
        getViewState().showError(message);
        loadingMore = false;
    }

    private void updateEventsList(List<EventDto> eventDtos) {
        getViewState().hideProgress();
        Log.d(TAG, "updateEventsList: "+ eventDtos.size());
        if (loadingMore) {
            adapterLogic.addMoreEvents(eventDtos);
            getViewState().isLoadingData(false);
        }else {
            adapterLogic.updateAllEvents(eventDtos);
            getViewState().isLoadingData(false);
        }
        loadingMore = false;

    }

    public void setFilterCategories(List<Integer> categories){
        interactor.setCategoryFilter(categories);
    }

    public void setFilterDates(Long start, Long end){
        Log.d(TAG, "setFilterDates: ");
        if (interactor.setDateFilter(start, end)){
            getViewState().setScrollListener();
            getEvents();
        }
        
    }

    public List<Integer> getFilterCategories(){
        return interactor.getCategoryFilter();
    }

    public Pair<Long, Long> getSelectedDates(){
        return interactor.getDateFilter();
    }

    public void setSearchQuery(String query){
        if(interactor.setSearchFilter(query)){
            getEvents();

        }
    }





    public String getSearchQuery(){
        return interactor.getSearchFilter();
    }

    public void openFilters(){
            getViewState().showFiltersDialog();
    }

  //  public void closeFilters(){
//        getViewState().showFiltersDialog();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        App.get().clearEventsListComponent();
    }



}
