package com.telran.ticketsapp.business.login;

import io.reactivex.Completable;

public interface LoginInteractor {
    Completable login(String email, String password);
    Completable recoverPassword(String email);
}
