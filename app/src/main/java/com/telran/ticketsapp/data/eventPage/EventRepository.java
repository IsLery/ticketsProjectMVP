package com.telran.ticketsapp.data.eventPage;

import com.telran.ticketsapp.data.eventPage.model.UIEvent;


import io.reactivex.Single;

public interface EventRepository {
    Single<UIEvent> getEvent(String id);
}
