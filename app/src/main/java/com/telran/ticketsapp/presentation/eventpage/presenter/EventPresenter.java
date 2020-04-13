package com.telran.ticketsapp.presentation.eventpage.presenter;

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
    @Inject
    EventInteractor interactor;

    Disposable disposable;

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
        getViewState().setEventInfo(event);
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
