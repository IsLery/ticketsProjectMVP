package com.telran.ticketsapp.data.provider.store;

public interface StoreProvidder {
    boolean saveToken(String token);
    boolean clearToken();
    String getToken();

}
