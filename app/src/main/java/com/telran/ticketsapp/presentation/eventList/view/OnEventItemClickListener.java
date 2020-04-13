package com.telran.ticketsapp.presentation.eventList.view;

import androidx.recyclerview.widget.RecyclerView;

public interface OnEventItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, int position);
}
