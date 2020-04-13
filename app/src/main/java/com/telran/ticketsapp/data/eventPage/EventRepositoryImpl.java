package com.telran.ticketsapp.data.eventPage;

import android.util.Log;

import com.telran.ticketsapp.data.eventPage.dto.RestTicketDto;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;
import com.telran.ticketsapp.data.provider.web.TicketsApi;
import com.telran.ticketsapp.data.utils.DataFormatMethods;

import java.io.IOException;

import io.reactivex.Single;
import retrofit2.Response;

public class EventRepositoryImpl implements EventRepository{
    private static final String TAG = "EventRepoImpl";
    private TicketsApi api;

    public EventRepositoryImpl(TicketsApi api) {
        this.api = api;
    }

    @Override
    public Single<UIEvent> getEvent(String id) {
        return api.getEvent(id).flatMap(eventDtoResponse -> onEventSuccess(eventDtoResponse));
    }

    private Single<UIEvent> onEventSuccess(Response<EventDto> response) throws IOException {
        if (response.isSuccessful()){
            return getRestTickets(response.body());
        }else if (response.code() == 404){
            String msg = DataFormatMethods.parseErrorMsg(response.errorBody().string());
            throw new RuntimeException(msg);
        }else {
            Log.d(TAG, "onRegistrationSuccess: "+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private Single<UIEvent> getRestTickets(EventDto eventDto){
        return api.getRestTickets(eventDto.getEventId()).flatMap(response -> onAllSuccess(response,eventDto));
    }

    private Single<UIEvent> onAllSuccess(Response<RestTicketDto> response, EventDto eventDto) throws IOException {
        if (response.isSuccessful()){
            return Single.just(parseFromDto(eventDto,response.body()));
        }else {
            Log.d(TAG, "onRegistrationSuccess: "+ response.errorBody().string());
            throw new RuntimeException("Server error! Call to support");
        }
    }

    private UIEvent parseFromDto(EventDto eventDto, RestTicketDto ticketsDto){
        return new UIEvent(eventDto.getEventId(),
                eventDto.getEventName(),
                eventDto.getArtist(),
                eventDto.getEventStart(),
                eventDto.getHall(),
                eventDto.getEventType(),
                eventDto.getDescription(),
                eventDto.getImages(),
                ticketsDto.maxPrice,
                ticketsDto.minPrice,
                ticketsDto.restTick);
    }
}
