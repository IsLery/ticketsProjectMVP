package com.telran.ticketsapp.data.login;

import io.reactivex.Completable;

public interface LoginRepository {
    Completable login(String email, String pass);
    Completable recoverPass(String email);
}
