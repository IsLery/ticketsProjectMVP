package com.telran.ticketsapp.data.registration;



import android.util.Log;

import com.telran.ticketsapp.data.registration.models.RegUserDto;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import java.io.IOException;

import io.reactivex.Completable;
import retrofit2.Response;

public class RegRepoImpl implements RegRepository {
    public static final String TAG = "RegRepository";
    TicketsApi api;

    public RegRepoImpl(TicketsApi api) {
        this.api = api;
    }

    @Override
    public Completable onRegistration(int gender, String firstName, String lastName, String email, String password, String phoneNumber) {
        RegUserDto regUserDto = new RegUserDto(gender, firstName,
                lastName, email,
                password, phoneNumber);
        return Completable.fromSingle(api.register(regUserDto).doOnSuccess(this::onRegistrationSuccess));
    }

    private void onRegistrationSuccess(Response<Void> response) throws IOException {
        if (response.isSuccessful()){
            //TODO hashedcode
        }else if (response.code() == 400){
            throw new RuntimeException(response.errorBody().string());
        }else {
            Log.d(TAG, "onRegistrationSuccess: "+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


}
