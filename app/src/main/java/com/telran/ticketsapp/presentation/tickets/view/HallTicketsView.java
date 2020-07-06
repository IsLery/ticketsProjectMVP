package com.telran.ticketsapp.presentation.tickets.view;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HallTicketsView extends MvpView {

    void selectTicket();
    void initHall(HallStructureDto hallInfo);
    void updateTickets(int count, double total);
    void showTicketsRv();
    void hideTicketsRv();
    @StateStrategyType(SkipStrategy.class)
    void showNextView();
    void showError(String msg);

}
