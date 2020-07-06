package com.telran.ticketsapp.presentation.cart.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telran.ticketsapp.databinding.SeatRowBinding;
import com.telran.ticketsapp.presentation.tickets.model.SeatModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartTicketsHolder> {
    List<SeatModel> list;
    Context context;

    public CartAdapter(List<SeatModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CartTicketsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SeatRowBinding binding = SeatRowBinding.inflate(inflater,parent,false);
        return new CartTicketsHolder(binding);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartTicketsHolder holder, int position) {
        SeatModel seatInfo = list.get(position);
        holder.binding.rowTxt.setText(seatInfo.getRow()+"");
        holder.binding.seatTxt.setText(seatInfo.getSeat());
    }

    class CartTicketsHolder extends RecyclerView.ViewHolder{
        SeatRowBinding binding;

        CartTicketsHolder(SeatRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
