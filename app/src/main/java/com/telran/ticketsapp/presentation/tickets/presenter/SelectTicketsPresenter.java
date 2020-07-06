package com.telran.ticketsapp.presentation.tickets.presenter;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.tickets.SelectTicketsInteractor;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.di.tickets.TicketsModule;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;
import com.telran.ticketsapp.presentation.tickets.view.CartTimerReceiver;
import com.telran.ticketsapp.presentation.tickets.view.HallTicketsView;
import com.telran.ticketsapp.presentation.tickets.view.SelectedTicketsAdapter;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SelectTicketsPresenter extends MvpPresenter<HallTicketsView> {
    SeatsAdapterLogic adapterLogic;
    private String eventId;
    private String eventInfo;
    private  TicketsInCartModel model;

    @Inject
    Context context;

    @Inject
    SelectTicketsInteractor interactor;
    Disposable disposable;

    public SelectTicketsPresenter() {
        App.get().plus(new TicketsModule()).inject(this);
        adapterLogic = new SelectedTicketsAdapter(context);
    }

    public void getTicketsInfo(String eventId){
        this.eventId = eventId;
        disposable = interactor.getTicketsInfo(eventId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetTicketsInfoSuccess);
    }

    public void setEventInfo(String eventInfo) {
        this.eventInfo = eventInfo;
    }

    public void addToSelected(String seatInfo){
        adapterLogic.addSeat(seatInfo);
        getViewState().updateTickets(adapterLogic.getItemCount(),adapterLogic.getSum());
        if (adapterLogic.getItemCount() > 0){
            getViewState().showTicketsRv();
        }
    }

    public void deleteFromSelected(String seatInfo){
        adapterLogic.deleteSeat(seatInfo);
        //TODO убирать выделение с зала, когда место удаляют в rv
        getViewState().updateTickets(adapterLogic.getItemCount(),adapterLogic.getSum());
        if (adapterLogic.getItemCount() == 0){
            getViewState().hideTicketsRv();
        }
    }


    public SelectedTicketsAdapter getAdapterInstance(){
        //TODO injectAdapter
        return (SelectedTicketsAdapter) adapterLogic;
    }


    private void onGetTicketsInfoSuccess(HallStructureDto info){
        getViewState().initHall(info);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        App.get().clearTicketsComponent();
    }

    public void bookTickets(){
        model = new TicketsInCartModel(eventId,eventInfo,adapterLogic.getSum(),adapterLogic.getSelectedSeatsAsList());
        disposable = interactor.bookTickets(eventId, model,
                adapterLogic.getSelectedSeats())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onBookingSuccess, throwable ->
                        onBookingError(throwable.getMessage()));
    }

    private void onBookingSuccess(){

        getViewState().showNextView();
    }

    private void onBookingError(String error){
        getViewState().showError(error);
    }


    public List<String> checkedSeats(){
        return adapterLogic.getSeatIds();
    }
}
