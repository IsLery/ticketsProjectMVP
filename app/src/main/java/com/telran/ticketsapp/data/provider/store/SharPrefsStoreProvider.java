package com.telran.ticketsapp.data.provider.store;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import java.util.concurrent.TimeUnit;


public class SharPrefsStoreProvider implements StoreProvider {
    private static final String AUTH = "user_auth";
    private static final String USER_TOKEN = "user_token";
    private static final String CART = "event_cart";
    private static final String BOOKED_DTO = "dto_in_cart";
    private static final String BOOKED_CART = "tickets_in_cart";
    Context context;
    Gson gson;

    public SharPrefsStoreProvider(Context context) {
        this.context = context;
        gson = new Gson();
    }

    @Override
    public boolean saveToken(String token) {
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE)
                .edit()
                .putString(USER_TOKEN,token)
                .commit();
    }

    @Override
    public boolean clearToken() {
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    @Override
    public String getToken() {
        return context.getSharedPreferences(AUTH,Context.MODE_PRIVATE).getString(USER_TOKEN,null);
    }

    @Override
    public boolean saveTickets(EventBookingDto bookingDto, TicketsInCartModel model) {
//        Handler handler = new android.os.Handler();
//        handler.post(() -> {
//            try {
//                Thread.sleep(TimeUnit.MINUTES.toMillis(10));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            deleteTickets();
//        });
        //TODO 10 min handler, update ui
        String booked = gson.toJson(bookingDto);
        String forCart = gson.toJson(model);
        return context.getSharedPreferences(CART,Context.MODE_PRIVATE)
                .edit()
                .putString(BOOKED_DTO,booked)
                .putString(BOOKED_CART,forCart)
                .commit();
    }

    @Override
    public boolean deleteTickets() {
        return  context.getSharedPreferences(CART,Context.MODE_PRIVATE)
                .edit()
                .clear()
                .commit();
    }

    @Override
    public EventBookingDto getTickets() {
        String booked = context.getSharedPreferences(CART,Context.MODE_PRIVATE)
                .getString(BOOKED_DTO,"");
        if (booked.isEmpty()){
            return null;
        }
        return gson.fromJson(booked,EventBookingDto.class);
    }

    @Override
    public TicketsInCartModel getTicketsForCart() {

        String booked = context.getSharedPreferences(CART,Context.MODE_PRIVATE)
                .getString(BOOKED_CART,"");
        if (booked.isEmpty()){
            return null;
        }
        return gson.fromJson(booked,TicketsInCartModel.class);
    }
}
