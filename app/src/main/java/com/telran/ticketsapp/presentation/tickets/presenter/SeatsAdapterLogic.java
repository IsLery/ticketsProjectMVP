package com.telran.ticketsapp.presentation.tickets.presenter;

import java.util.List;

public interface SeatsAdapterLogic {
    void addSeat(String seat);
    void deleteSeat(String seat);
    double getSum();
    int getItemCount();
    List<String> getSeatIds();
}
