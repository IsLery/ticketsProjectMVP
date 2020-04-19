package com.telran.ticketsapp.presentation.tickets.presenter;


import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.tickets.SelectTicketsInteractor;
import com.telran.ticketsapp.data.eventsList.dto.EventDateHallDto;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.di.tickets.TicketsModule;
import com.telran.ticketsapp.presentation.tickets.view.HallTicketsView;
import com.telran.ticketsapp.presentation.tickets.view.SelectedTicketsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SelectTicketsPresenter extends MvpPresenter<HallTicketsView> {
    SeatsAdapterLogic adapterLogic;

    @Inject
    Context context;

    @Inject
    SelectTicketsInteractor interactor;
    Disposable disposable;

    public SelectTicketsPresenter() {
        App.get().plus(new TicketsModule()).inject(this);
        adapterLogic = new SelectedTicketsAdapter(new ArrayList<>(),context);
    }

    public void getTicketsInfo(String eventId){
        disposable = interactor.getTicketsInfo(eventId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetTicketsInfoSuccess);
    }

    public void addToSelected(String seatInfo){
        adapterLogic.addSeat(seatInfo);
        getViewState().updateTickets(adapterLogic.getItemCount(),adapterLogic.getSum());
    }

    public void deleteFromSelected(String seatInfo){
        adapterLogic.deleteSeat(seatInfo);
        getViewState().updateTickets(adapterLogic.getItemCount(),adapterLogic.getSum());
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

    public List<String> checkedSeats(){
        return adapterLogic.getSeatIds();
    }
}
