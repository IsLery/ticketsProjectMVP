package com.telran.ticketsapp.presentation.payment.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.telran.ticketsapp.presentation.payment.view.PayingView;

@InjectViewState
public class PaymentPresenter extends MvpPresenter<PayingView> {

    public void allowPayment(boolean allow){
        getViewState().unblockPayBtn(allow);
    }
    public void onPaymentSuccess(){
        getViewState().showSuccess();
    }
}
