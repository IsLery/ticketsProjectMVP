package com.telran.ticketsapp.business.eventPage;

import com.telran.ticketsapp.data.eventPage.model.UIEvent;


import io.reactivex.Single;

public interface EventInteractor {
    public Single<UIEvent> getEvent(String id);
}
