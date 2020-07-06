package com.telran.ticketsapp.presentation.tickets.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telran.ticketsapp.databinding.SeatRowBinding;
import com.telran.ticketsapp.presentation.tickets.model.SeatModel;
import com.telran.ticketsapp.presentation.tickets.presenter.SeatsAdapterLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectedTicketsAdapter extends RecyclerView.Adapter<SelectedTicketsAdapter.SelectedSeatsHolder>implements SeatsAdapterLogic {
    private static final String TAG = "SelectedTicketsAdapter";
    private List<SeatModel> selectedSeats;
    private List<String> ids;
    private Map<String,List<String>> seatsMap;
    private Context context;
    private Double sum;
    private OnSelectedSeatClickListener listener;
    private Map<String, CompoundButton> hallBtns;


    public SelectedTicketsAdapter( Context context) {
        selectedSeats = new ArrayList<>();
        this.context = context;
        seatsMap = new HashMap<>();
        ids = new ArrayList<>();
        sum = 0.0;
    }

    public void setHallBtns(Map<String, CompoundButton> hallBtns) {
        this.hallBtns = hallBtns;
    }

    public void setListener(OnSelectedSeatClickListener listener) {
        this.listener = listener;
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
        Log.d(TAG, "onBindViewHolder: "+seatInfo.getRow()+" "+seatInfo.getSeat());
        holder.binding.rowTxt.setText(String.valueOf(seatInfo.getRow()));
        holder.binding.seatTxt.setText(seatInfo.getSeat());
        holder.binding.deleteBtn.setOnClickListener(v -> {
           deleteSeatInRv(seatInfo);
        });
    }

    @Override
    public int getItemCount() {
        return selectedSeats.size();
    }


    @Override
    public void addSeat(String seat) {
        SeatModel seatModel = new SeatModel(seat);
        String row = String.valueOf(seatModel.getRow());
        if (!seatsMap.containsKey(row)) {
            seatsMap.put(row, new ArrayList<>());
        }
        List<String> seatsInRow = seatsMap.get(row);
        seatsInRow.add(seatModel.getSeat());
        Log.d(TAG, "addSeat: "+seatsInRow.size() + " row "+row);

        ids.add(seat);
        int pos = ~Collections.binarySearch(selectedSeats,seatModel);
        selectedSeats.add(pos,seatModel);
        sum += seatModel.getPrice();
        notifyItemInserted(pos);
    }

    @Override
    public void deleteSeat(String seat) {
        SeatModel model = new SeatModel(seat);
        ids.remove(seat);
        selectedSeats.remove(model);
        String row = String.valueOf(model.getRow());
        List<String> seatsInRow = seatsMap.get(row);
        seatsInRow.remove(model.getSeat());
        if (seatsInRow.size() == 0){
            seatsMap.remove(row);
        }
        sum -= model.getPrice();
        notifyDataSetChanged();
    }


    public void deleteSeatInRv(SeatModel model) {
        Log.d(TAG, "deleteSeatInRv: "+model);
        CompoundButton btn = hallBtns.get(model.getId());
        btn.setChecked(false); //onclicklistener will be called
    }

    @Override
    public double getSum() {
        return sum;
    }

    @Override
    public List<String> getSeatIds() {
        return ids;
    }

    @Override
    public Map<String, List<String>> getSelectedSeats() {
        return seatsMap;
    }

    @Override
    public List<SeatModel> getSelectedSeatsAsList() {
        return selectedSeats;
    }

    class SelectedSeatsHolder extends RecyclerView.ViewHolder{
        SeatRowBinding binding;

        SelectedSeatsHolder(SeatRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
