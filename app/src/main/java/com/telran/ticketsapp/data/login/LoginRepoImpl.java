package com.telran.ticketsapp.data.login;

import android.util.Log;

import com.google.gson.Gson;
import com.telran.ticketsapp.data.login.models.ErrorDto;
import com.telran.ticketsapp.data.login.models.LoginInDto;
import com.telran.ticketsapp.data.login.models.LoginOutDto;
import com.telran.ticketsapp.data.login.models.PasswordRecoveryDto;
import com.telran.ticketsapp.data.provider.store.StoreProvidder;
import com.telran.ticketsapp.data.provider.web.TicketsApi;

import java.io.IOException;

import io.reactivex.Completable;
import okhttp3.MediaType;
import retrofit2.Response;

public class LoginRepoImpl implements LoginRepository {
    public static final String TAG = "LoginRepository";
    TicketsApi api;
    StoreProvidder providder;

    public LoginRepoImpl(TicketsApi api, StoreProvidder providder) {
        this.api = api;
        this.providder = providder;
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
            String msg = parseErrorMsg(response.errorBody().string());
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
            providder.saveToken(loginOutDto.getToken());
        }else if(response.code() == 400 || response.code() == 404){
            String msg = parseErrorMsg(response.errorBody().string());
            throw new RuntimeException(msg);
        }else {
            Log.d(TAG, "onLoginSuccess: error"+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private String parseErrorMsg(String string) {
        Gson gson = new Gson();
        ErrorDto errorDto = gson.fromJson(string,ErrorDto.class);
        return errorDto.getMessage();
    }
}
