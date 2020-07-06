package com.telran.ticketsapp.presentation.tickets.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.telran.ticketsapp.App;
import com.telran.ticketsapp.data.provider.store.SharPrefsStoreProvider;
import com.telran.ticketsapp.data.provider.store.StoreProvider;
import com.telran.ticketsapp.presentation.MainActivity;
import com.telran.ticketsapp.presentation.cart.view.CartFragment;

import java.util.Objects;

import javax.inject.Inject;

public class CartTimerReceiver extends BroadcastReceiver {

    @Inject
    StoreProvider provider;

    public CartTimerReceiver() {

        App.get().getAppComponent().injectsCartTimerReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //todo поправить каст активити
        provider.deleteTickets();
//        Fragment fragment = ((MainActivity)context).getSupportFragmentManager().getPrimaryNavigationFragment();
//        if (fragment != null && fragment instanceof CartFragment){
//           fragment.requireView().invalidate();
//        }
    }
}
