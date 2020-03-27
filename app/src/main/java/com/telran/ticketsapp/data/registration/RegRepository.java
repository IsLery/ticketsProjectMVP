package com.telran.ticketsapp.data.registration;

import io.reactivex.Completable;

public interface RegRepository {
    Completable onRegistration(int gender,
                               String firstName,
                               String lastName,
                               String email,
                               String password,
                               String phoneNumber);
}
