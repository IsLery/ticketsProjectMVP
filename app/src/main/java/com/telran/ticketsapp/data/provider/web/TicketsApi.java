package com.telran.ticketsapp.data.provider.web;


import com.telran.ticketsapp.data.eventPage.dto.RestTicketDto;
import com.telran.ticketsapp.data.eventsList.dto.EventDateHallDto;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;
import com.telran.ticketsapp.data.login.models.LoginInDto;
import com.telran.ticketsapp.data.login.models.LoginOutDto;
import com.telran.ticketsapp.data.login.models.PasswordRecoveryDto;
import com.telran.ticketsapp.data.registration.models.RegUserDto;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TicketsApi {
    @POST("user")
    Single<Response<Void>> register(@Body RegUserDto userDto);

    @POST("login")
    Single<Response<LoginOutDto>> login(@Body LoginInDto loginDto);

    @POST("user/password")
    Single<Response<Void>> recoverPassword(@Body PasswordRecoveryDto recoveryDto);


    @GET("user/{hashedCode}")
    Single<Response<Void>> validateEmail(@Path("hashedCode") String hashedCode);
    //TODO Разобраться, что за метод такой

    @GET("events/{page}/{page_size}")
    Single<Response<List<EventDto>>> getCurrentEvents(@Path("page") int page, @Path("page_size") int pageSize);

    @POST("events/bydate/{page}/{page_size}")
    Single<Response<List<EventDto>>> getByDates(@Path("page") int page, @Path("page_size") int pageSize, @Body EventDateHallDto dateHallDto);

    @GET("event/{eventId}")
    Single<Response<EventDto>> getEvent(@Path("eventId") String id);

    @GET("events/rest/{eventId}")
    Single<Response<RestTicketDto>> getRestTickets(@Path("eventId") String id);

    @GET("event/{eventId}/{isShort}")
    Single<Response<HallStructureDto>> getEventTicketsInfo(@Path("eventId") String id, @Path("isShort") boolean isShort);
}
