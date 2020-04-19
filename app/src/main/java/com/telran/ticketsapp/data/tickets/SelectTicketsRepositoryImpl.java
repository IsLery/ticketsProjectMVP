package com.telran.ticketsapp.data.tickets;

import android.util.Log;

import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.utils.DataFormatMethods;

import java.io.IOException;

import io.reactivex.Single;
import retrofit2.Response;

public class SelectTicketsRepositoryImpl implements SelectTicketsRepository{
    private static final String TAG = "SelectTicketsRepo";
    TicketsApi api;

    public SelectTicketsRepositoryImpl(TicketsApi api) {
        this.api = api;
    }

    @Override
    public Single<HallStructureDto> getTicketsInfo(String eventId, boolean isShort) {
        return api.getEventTicketsInfo(eventId, isShort).flatMap(this::onGetTicketsInfoSuccess);
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

}
