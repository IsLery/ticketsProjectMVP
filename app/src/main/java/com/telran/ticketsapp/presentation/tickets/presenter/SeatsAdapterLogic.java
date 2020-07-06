package com.telran.ticketsapp.presentation.tickets.presenter;

import com.telran.ticketsapp.presentation.tickets.model.SeatModel;
import com.telran.ticketsapp.presentation.tickets.view.SelectedTicketsAdapter;

import java.util.List;
import java.util.Map;

public interface SeatsAdapterLogic {
    void addSeat(String seat);
    void deleteSeat(String seat);
    double getSum();
    int getItemCount();
    List<String> getSeatIds();
    Map<String, List<String>> getSelectedSeats();
    List<SeatModel> getSelectedSeatsAsList();
}
