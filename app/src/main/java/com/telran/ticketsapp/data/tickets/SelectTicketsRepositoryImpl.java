package com.telran.ticketsapp.data.tickets;

import android.util.Log;

import com.google.gson.Gson;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.LockedSeats;
import com.telran.ticketsapp.data.utils.DataFormatMethods;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class SelectTicketsRepositoryImpl implements SelectTicketsRepository{
    private static final String TAG = "SelectTicketsRepo";
    TicketsApi api;
    StoreProvider provider;


    public SelectTicketsRepositoryImpl(TicketsApi api, StoreProvider provider) {
        this.api = api;
        this.provider = provider;
    }

    @Override
    public Single<HallStructureDto> getTicketsInfo(String eventId, boolean isShort) {
        return api.getEventTicketsInfo(eventId, isShort).flatMap(this::onGetTicketsInfoSuccess);
    }

    @Override
    public Completable bookTickets(String eventId, TicketsInCartModel model, Map<String, List<String>> seats) {
        EventBookingDto dto = mapToEventBookingDto(eventId, seats);
        return Completable.fromSingle(api.bookEvents(dto).doOnSuccess(response -> onBookTicketsSuccess(response,dto, model)));
    }

    private void onBookTicketsSuccess(Response<Void> response, EventBookingDto dto, TicketsInCartModel model) throws IOException {
        if (response.isSuccessful()){
            provider.saveTickets(dto, model);
        }else {
            Log.d(TAG, "onBookTicketsSuccess: error" + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private Single<HallStructureDto> onGetTicketsInfoSuccess(Response<HallStructureDto> response) throws IOException {
        if (response.isSuccessful()){
           return Single.just(response.body());
        }else if (response.code() == 404){
            throw new RuntimeException(DataFormatMethods.parseErrorMsg(response.errorBody().string()));
        }else {
            Log.d(TAG, "onRegistrationSuccess: "+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private EventBookingDto mapToEventBookingDto(String eventId,Map<String, List<String>> seats){
        Set<String> rows = seats.keySet();

        List<LockedSeats> lockedSeatsList = new ArrayList<>();
        for (String r: rows) {
            LockedSeats locked = new LockedSeats(r,seats.get(r));
            lockedSeatsList.add(locked);
        }
        return new EventBookingDto(eventId,lockedSeatsList);
    }

}
