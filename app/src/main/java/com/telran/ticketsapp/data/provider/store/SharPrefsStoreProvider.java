package com.telran.ticketsapp.data.provider.store;

import android.content.Context;

public class SharPrefsStoreProvider implements StoreProvider {
    private static final String AUTH = "user_auth";
    private static final String USER_TOKEN = "user_token";
    Context context;

    public SharPrefsStoreProvider(Context context) {
        this.context = context;
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
}
