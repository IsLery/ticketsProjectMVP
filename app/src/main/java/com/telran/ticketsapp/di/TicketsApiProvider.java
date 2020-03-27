package com.telran.ticketsapp.di;

import com.telran.ticketsapp.data.provider.web.TicketsApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketsApiProvider {
    private static TicketsApiProvider instance = new TicketsApiProvider();
    private TicketsApi api;

    private TicketsApiProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ticketserviceapp.herokuapp.com")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(TicketsApi.class);
    }

    public static TicketsApiProvider getInstance(){
        return instance;
    }

    public TicketsApi getApi() {
        return api;
    }
}
