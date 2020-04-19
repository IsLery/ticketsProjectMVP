package com.telran.ticketsapp.presentation.eventpage.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.eventPage.EventInteractor;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;
import com.telran.ticketsapp.di.eventPage.EventModule;
import com.telran.ticketsapp.presentation.eventpage.view.EventView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class EventPresenter extends MvpPresenter<EventView> {
    private static final String TAG = "EventPresenter";

    @Inject
    EventInteractor interactor;

    private Disposable disposable;

    private UIEvent eventUI;

    public EventPresenter() {
        App.get().plus(new EventModule()).inject(this);
    }

    public void getEvent(String id){
        disposable = interactor.getEvent(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetEventSuccess,throwable -> {
                    onError(throwable.getMessage());
                });
    }

    private void onError(String message) {
    }

    private void onGetEventSuccess(UIEvent event) {
        eventUI = event;
        getViewState().setEventInfo(event);
    }

    public void toNextView(){
        getViewState().showNextView(eventUI);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.get().clearEventComponent();
        if (disposable != null){
            disposable.dispose();
        }
    }
}
