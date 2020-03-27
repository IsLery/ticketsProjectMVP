package com.telran.ticketsapp.presentation.eventList.presenter;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.business.eventsList.EventListInteractor;
import com.telran.ticketsapp.data.eventsList.EndOfListException;
import com.telran.ticketsapp.data.eventsList.models.EventDto;
import com.telran.ticketsapp.di.EventsDependenceFactory;
import com.telran.ticketsapp.presentation.eventList.view.EventListAdapter;
import com.telran.ticketsapp.presentation.eventList.view.EventListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventListPresenter extends MvpPresenter<EventListView> {
    public static final String TAG = "EventListPresenter";
    private boolean loadingMore = false;
    Disposable disposable;

    Context context;

    EventListInteractor interactor;

    boolean lastPageReached;

    public EventListPresenter() {
        interactor = EventsDependenceFactory.getInstance().getInteractor();
        context = EventsDependenceFactory.getInstance().getContext();

    }


    public void getCurrentEvents(){
        if (lastPageReached){
            return;
        }
        Log.d(TAG, "getCurrentEvents: start");
        getViewState().showProgress();
        disposable = interactor.getCurrentEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateEventsList,throwable ->
                {   if (throwable instanceof EndOfListException){
                    lastPageReached = true;
                    getViewState().hideProgress();
                }else {
                    showError(throwable.getMessage());}});
    }

    public void getEventsInRange(long start, long end){
        if (lastPageReached){
            return;
        }
        Log.d(TAG, "getEventsInRange: start");
        getViewState().showProgress();
        disposable = interactor.getEventsInDateRange(start, end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateEventsList,throwable ->
                {   if (throwable instanceof EndOfListException){
                    lastPageReached = true;
                    getViewState().hideProgress();
                }else {
                    showError(throwable.getMessage());}});
    }

    public void loadmore(){
        loadingMore = true;
        getCurrentEvents();
    }

    public void loadMoreInRange(long start, long end){
        loadingMore = true;
        getEventsInRange(start, end);
    }


    private void showError(String message) {
        getViewState().hideProgress();
        getViewState().showError(message);
    }

    private void updateEventsList(List<EventDto> eventDtos) {
        getViewState().hideProgress();
        Log.d(TAG, "updateEventsList: "+ eventDtos.size());
        if (loadingMore) {
            getViewState().loadMoreData(eventDtos);
        }else {
            getViewState().updateAll(eventDtos);
        }
        loadingMore = false;

    }


    public void filterByCategory(ArrayList<Integer> selectedCat){
        Log.d(TAG, "filterByCategory: "+selectedCat.size());
        disposable = interactor.getFilteredByCategory(selectedCat).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateEventsList,throwable -> showError(throwable.getMessage()));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
