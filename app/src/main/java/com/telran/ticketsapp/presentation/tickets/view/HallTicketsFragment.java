package com.telran.ticketsapp.presentation.tickets.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.eventsList.dto.PriceRanges;
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.PriceRangeDto;
import com.telran.ticketsapp.databinding.FragmentHallTicketsBinding;
import com.telran.ticketsapp.presentation.tickets.presenter.SelectTicketsPresenter;
import com.telran.ticketsapp.presentation.tickets.view.halls.SmallHall;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class HallTicketsFragment extends MvpAppCompatFragment implements HallTicketsView {
    FragmentHallTicketsBinding binding;

    private static final String TAG = "HallTicketsFragment";
    private static final String EVENT_ID = "event_id";
    private static final String HALL_TYPE = "hall_type";
    private static final String EVENT_FULL_TITLE = "full_title_of_event";

    private int hallType;

    @InjectPresenter
    public SelectTicketsPresenter presenter;

    public static Bundle makeBundleForHallTicketsFragment(String eventId, int hallType, String artist, String eventTitle, String eventDate){
        Bundle args = new Bundle();
        args.putString(EVENT_ID,eventId);
        args.putInt(HALL_TYPE,hallType);
        String eventFullTitle = artist+" | "+ eventTitle + " | " + eventDate;
        args.putString(EVENT_FULL_TITLE,eventFullTitle);
        return args;
    }

    public HallTicketsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        String eventId = arguments.getString(EVENT_ID,"");
        hallType = arguments.getInt(HALL_TYPE);
        presenter.getTicketsInfo(eventId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: ");


        binding = FragmentHallTicketsBinding.inflate(inflater,container,false);
        binding.eventInfo.setText(getArguments().getString(EVENT_FULL_TITLE,""));
        binding.selectedSeats.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.selectedSeats.setAdapter(presenter.getAdapterInstance());


     //   String[] ranges = {"100$","200$","300$"};
//        ArrayAdapter<String> rangeAdapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_list_item_1,ranges);
//        binding.priceRanges.setAdapter(rangeAdapter);

        return binding.getRoot();
    }

    @Override
    public void proceedToCart() {

    }


    @Override
    public void selectTicket() {

    }

    @Override
    public void initHall(HallStructureDto hallInfo) {
        List<PriceRangeDto> ranges = hallInfo.getPriceRanges();
        List<String> prices = new ArrayList<>();
        ArrayAdapter<String> rangeAdapter = new ArrayAdapter<>(requireContext(),android.R.layout.simple_list_item_1,prices);
        binding.priceRanges.setAdapter(rangeAdapter);
        for (PriceRangeDto r: ranges
             ) {
            prices.add(getString(R.string.price_range,r.getPrice()));
        }
        if (binding.hallScroll.getChildCount() > 0){
            return;
        }
        if (hallType == 1) {
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked){
                    presenter.addToSelected(buttonView.getTag().toString());
                }
                else{
                    presenter.deleteFromSelected(buttonView.getTag().toString());
                }
            };
            binding.hallScroll.addView(SmallHall.createSmallHallView(requireContext(), hallInfo,listener, presenter.checkedSeats()));
            binding.getRoot().invalidate();
        }

        }

    @Override
    public void updateTickets(int count, double total) {
        String qty = getResources().getQuantityString(R.plurals.tickets_qty,count,count);
        //TODO save checked state
        binding.ticketsTotalTxt.setText(qty);
        binding.ticketsSumTxt.setText(getString(R.string.price_range,total));
    }


}
