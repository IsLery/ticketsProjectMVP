package com.telran.ticketsapp.presentation.tickets.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.tickets.dto.PriceRangeDto;
import com.telran.ticketsapp.databinding.PriceRowBinding;
import java.util.List;


public class PriceRangesAdapter extends RecyclerView.Adapter<PriceRangesAdapter.PriceRangeHolder> {
    List<PriceRangeDto> rangeList;
    Context context;

    public PriceRangesAdapter(List<PriceRangeDto> rangeList, Context context) {
        this.rangeList = rangeList;
        this.context = context;
    }

    @NonNull
    @Override
    public PriceRangeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PriceRowBinding binding = PriceRowBinding.inflate(LayoutInflater.from(context),parent,false);
        return new PriceRangeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceRangeHolder holder, int position) {
       PriceRangeDto r = rangeList.get(position);
        holder.binding.priceTxt.setText(context.getString(R.string.price_range,r.getPrice()));
        holder.binding.priceTxt.setBackgroundColor(Color.parseColor(r.getColor()));
    }

    @Override
    public int getItemCount() {
        return rangeList.size();
    }

    class PriceRangeHolder extends RecyclerView.ViewHolder{
        PriceRowBinding binding;

        public PriceRangeHolder(@NonNull PriceRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
