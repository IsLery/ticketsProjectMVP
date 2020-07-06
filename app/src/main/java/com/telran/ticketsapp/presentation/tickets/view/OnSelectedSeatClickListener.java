package com.telran.ticketsapp.presentation.tickets.view;

import androidx.recyclerview.widget.RecyclerView;

public interface OnSelectedSeatClickListener {
    void onSeatClick(RecyclerView.Adapter adapter, String seatInfo);

}
