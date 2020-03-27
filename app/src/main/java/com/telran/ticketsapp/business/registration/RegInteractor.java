package com.telran.ticketsapp.business.registration;

import io.reactivex.Completable;

public interface RegInteractor {
    Completable onRegistration(int gender,
                               String firstName,
                               String lastName,
                               String email,
                               String password,
                               String phoneNumber);
}
