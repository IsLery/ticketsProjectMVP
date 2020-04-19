package com.telran.ticketsapp.presentation.tickets.view;


import com.arellomobile.mvp.MvpView;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;

public interface HallTicketsView extends MvpView {
    void proceedToCart();

    void selectTicket();
    void initHall(HallStructureDto hallInfo);
    void updateTickets(int count, double total);
}
