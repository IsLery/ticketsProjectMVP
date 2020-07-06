package com.telran.ticketsapp.data.cart;

import android.util.Log;

import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;

public class CartRepositoryImpl implements CartRepository {
    private static final String TAG = "CartRepositoryImpl" ;
    TicketsApi api;
    StoreProvider provider;

    public CartRepositoryImpl(TicketsApi api, StoreProvider provider) {
        this.api = api;
        this.provider = provider;
    }

    @Override
    public Single<TicketsInCartModel> getCartInfo() {
        TicketsInCartModel model = provider.getTicketsForCart();
        if (model == null){
           return Single.error(new EmptyCartException());
        }
        return Single.just(provider.getTicketsForCart());
    }

    @Override
    public Completable clearCart() {
        provider.deleteTickets();
        return Completable.complete();
//        EventBookingDto dto = provider.getTickets();
//        return Completable.fromSingle(api.deleteBooking(dto).doOnSuccess(this::onBookingDeleted));
    }

    private void onBookingDeleted(Response<Boolean> response) throws IOException {
        if (response.isSuccessful()){
            if (response.body()) {
                provider.deleteTickets();
            }
        }else {
            Log.d(TAG, "onBookingDeleted: "+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }


}
