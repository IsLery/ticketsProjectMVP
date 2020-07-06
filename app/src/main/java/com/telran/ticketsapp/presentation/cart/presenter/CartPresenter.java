package com.telran.ticketsapp.presentation.cart.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.App;
import com.telran.ticketsapp.business.cart.CartInteractor;
import com.telran.ticketsapp.data.cart.EmptyCartException;
import com.telran.ticketsapp.di.cart.CartModule;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;
import com.telran.ticketsapp.presentation.cart.view.CartAdapter;
import com.telran.ticketsapp.presentation.cart.view.CartView;
import com.telran.ticketsapp.presentation.tickets.view.CartTimerReceiver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CartPresenter extends MvpPresenter<CartView> {
    @Inject
    CartInteractor interactor;

    Disposable disposable;

    @Inject
    Context context;
    CartAdapter adapter;

    TicketsInCartModel model;

    public CartPresenter() {
        App.get().plus(new CartModule()).inject(this);
    }

    public void getTicketsInCart(){
        disposable = interactor.getCartInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTicketsDataReceived,this::onCartError);
    }

    public void deleteFromCart(){
        disposable = interactor.clearCart().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCartCleared);
    }

    private void onTicketsDataReceived(TicketsInCartModel model) {
            this.model = model;
            adapter = new CartAdapter(model.getTickets(),context);
            getViewState().setData(model.getEventInfo(),model.getSum(),adapter);
    }

    private void onCartCleared(){
        adapter = null;
        clearCartTimer();
        getViewState().clearCart();
    }

    @Override
    public void onDestroy() {
        App.get().clearCartComponent();
        if (disposable != null){
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void onCartError(Throwable e){
        if (e instanceof EmptyCartException){
            getViewState().clearCart();
        }
    }

    public void proceedToPayment(){
        if (model != null) {
            Bundle bundle = new Bundle();
            bundle.putString("eventInfo", model.getEventInfo());
            bundle.putInt("ticketsQty",adapter.getItemCount());
            bundle.putFloat("ticketsSum",model.getSum().floatValue());
            getViewState().showNextView(bundle);
            clearCartTimer();
        }
    }

    private void clearCartTimer(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CartTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


}
