package com.telran.ticketsapp.data.login;

import android.util.Log;

import com.telran.ticketsapp.data.login.models.LoginInDto;
import com.telran.ticketsapp.data.login.models.LoginOutDto;
import com.telran.ticketsapp.data.login.models.PasswordRecoveryDto;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.utils.DataFormatMethods;

import java.io.IOException;

import io.reactivex.Completable;
import retrofit2.Response;

public class LoginRepoImpl implements LoginRepository {
    public static final String TAG = "LoginRepository";
    TicketsApi api;
    StoreProvider provider;

    public LoginRepoImpl(TicketsApi api, StoreProvider provider) {
        this.api = api;
        this.provider = provider;
    }

    @Override
    public Completable login(String email, String pass) {
        LoginInDto loginInDto = new LoginInDto(email,pass);
        return Completable.fromSingle(api.login(loginInDto).doOnSuccess(this::onLoginSuccess));
    }

    @Override
    public Completable recoverPass(String email) {
        PasswordRecoveryDto recoveryDto = new PasswordRecoveryDto(email);
        return Completable.fromSingle(api.recoverPassword(recoveryDto).doOnSuccess(this::onRecoverySuccess));
    }

    private void onRecoverySuccess(Response<Void> response) throws IOException {
        if (response.isSuccessful()) {
            Log.d(TAG, "onRecoverySuccess: success");
        } else if (response.code() == 400 || response.code() == 404) {
            String msg = DataFormatMethods.parseErrorMsg(response.errorBody().string());
            throw new RuntimeException(msg);
        } else {
            Log.d(TAG, "onRecoverySuccess: error" + response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


    private void onLoginSuccess(Response<LoginOutDto> response) throws IOException {
        if (response.isSuccessful()){
            LoginOutDto loginOutDto = response.body();
            Log.d(TAG, "onLoginSuccess: success"+loginOutDto.getToken());
            provider.saveToken(loginOutDto.getToken());
        }else if(response.code() == 400 || response.code() == 404){
            String msg = DataFormatMethods.parseErrorMsg(response.errorBody().string());
            throw new RuntimeException(msg);
        }else {
            Log.d(TAG, "onLoginSuccess: error"+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


}
