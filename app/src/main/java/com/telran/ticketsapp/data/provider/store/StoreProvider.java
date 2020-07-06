package com.telran.ticketsapp.data.provider.store;

import com.telran.ticketsapp.data.tickets.dto.EventBookingDto;
import com.telran.ticketsapp.presentation.cart.model.TicketsInCartModel;

public interface StoreProvider {
    boolean saveToken(String token);
    boolean clearToken();
    String getToken();
    boolean saveTickets(EventBookingDto bookingDto, TicketsInCartModel model);
    boolean deleteTickets();
    EventBookingDto getTickets();
    TicketsInCartModel getTicketsForCart();


}
