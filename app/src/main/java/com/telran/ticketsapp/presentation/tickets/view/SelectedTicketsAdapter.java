package com.telran.ticketsapp.presentation.tickets.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telran.ticketsapp.databinding.SeatRowBinding;
import com.telran.ticketsapp.presentation.tickets.presenter.SeatsAdapterLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectedTicketsAdapter extends RecyclerView.Adapter<SelectedTicketsAdapter.SelectedSeatsHolder>implements SeatsAdapterLogic {
    private static final String TAG = "SelectedTicketsAdapter";
    private List<SeatModel> selectedSeats;
    private List<String> ids;
    private Context context;
    private Double sum;


    public SelectedTicketsAdapter(List<SeatModel> selectedSeats, Context context) {
        this.selectedSeats = selectedSeats;
        this.context = context;
        ids = new ArrayList<>();
        sum = 0.0;
    }

    @NonNull
    @Override
    public SelectedSeatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        SeatRowBinding binding = SeatRowBinding.inflate(inflater,parent,false);
        return new SelectedSeatsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedSeatsHolder holder, int position) {
        SeatModel seatInfo = selectedSeats.get(position);
        Log.d(TAG, "onBindViewHolder: "+seatInfo.row+" "+seatInfo.seat);
        holder.binding.rowTxt.setText(seatInfo.row);
        holder.binding.seatTxt.setText(seatInfo.seat);
    }

    @Override
    public int getItemCount() {
        return selectedSeats.size();
    }


    @Override
    public void addSeat(String seat) {
        SeatModel seatModel = new SeatModel(seat);
        ids.add(seat);
        selectedSeats.add(seatModel);
        sum += seatModel.price;
        notifyDataSetChanged();
    }



    @Override
    public void deleteSeat(String seat) {
        SeatModel model = new SeatModel(seat);
        ids.remove(seat);
        selectedSeats.remove(model);
        sum -= model.price;
        notifyDataSetChanged();
    }

    @Override
    public double getSum() {
        return sum;
    }

    @Override
    public List<String> getSeatIds() {
        return ids;
    }

    class SelectedSeatsHolder extends RecyclerView.ViewHolder{
        SeatRowBinding binding;

        SelectedSeatsHolder(SeatRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    class SeatModel{
        String id;
        String row;
        String seat;
        Double price;

        public SeatModel(String id) {
            this.id = id;
            String[] info = id.split("-");
            row = info[0];
            seat = info[1];
            price = Double.parseDouble(info[2]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SeatModel seatModel = (SeatModel) o;
            return Objects.equals(id, seatModel.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
