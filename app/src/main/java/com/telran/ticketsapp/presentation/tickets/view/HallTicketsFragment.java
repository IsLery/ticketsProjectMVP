package com.telran.ticketsapp.presentation.tickets.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.telran.ticketsapp.data.tickets.dto.HallStructureDto;
import com.telran.ticketsapp.data.tickets.dto.PriceRangeDto;
import com.telran.ticketsapp.databinding.FragmentHallTicketsBinding;
import com.telran.ticketsapp.presentation.tickets.presenter.SelectTicketsPresenter;
import com.telran.ticketsapp.presentation.tickets.view.halls.HallConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class HallTicketsFragment extends MvpAppCompatFragment implements HallTicketsView, View.OnClickListener {
    FragmentHallTicketsBinding binding;

    private static final String TAG = "HallTicketsFragment";
    private static final String EVENT_ID = "event_id";
    private static final String HALL_TYPE = "hall_type";
    private static final String EVENT_FULL_TITLE = "full_title_of_event";

    private int hallType;
    private SelectedTicketsAdapter adapter;

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
        String fullInfo = getArguments().getString(EVENT_FULL_TITLE,"");
        presenter.setEventInfo(fullInfo);
        binding.eventInfo.setText(fullInfo);
        binding.selectedSeats.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = presenter.getAdapterInstance();
        binding.selectedSeats.setAdapter(adapter);
        binding.toCartBtn.setOnClickListener(this);
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL);
        binding.selectedSeats.addItemDecoration(decoration);
        binding.priceRanges.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

    @Override
    public void selectTicket() {

    }

    @Override
    public void initHall(HallStructureDto hallInfo) {
        List<PriceRangeDto> ranges = hallInfo.getPriceRanges();

        PriceRangesAdapter rangeAdapter = new PriceRangesAdapter(ranges,requireContext());
        binding.priceRanges.setAdapter(rangeAdapter);
        if (binding.hallScroll.getChildCount() > 0){
            return;
        }
        HallConstructor constructor = new HallConstructor(requireContext());
            CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
                if (isChecked){
                    presenter.addToSelected(buttonView.getTag().toString());
                }
                else{
                    presenter.deleteFromSelected(buttonView.getTag().toString());
                }
            };
            if (hallType == 1) {
                binding.hallScroll.addView(constructor.createSmallHallView(hallInfo, listener, presenter.checkedSeats()));
                binding.getRoot().invalidate();
            }else if (hallType == 0){
                binding.hallScroll.addView(constructor.createBigHallView(hallInfo, listener, presenter.checkedSeats()));
                binding.getRoot().invalidate();
        }
            adapter.setHallBtns(constructor.getAllButtons());
        }

    @Override
    public void updateTickets(int count, double total) {
        String qty = getResources().getQuantityString(R.plurals.tickets_qty,count,count);
        //TODO save checked state
        binding.ticketsTotalTxt.setText(qty);
        binding.ticketsSumTxt.setText(getString(R.string.price_range,total));
    }

    @Override
    public void showTicketsRv() {
        binding.showIfselected.setVisibility(View.VISIBLE);
        binding.toCartBtn.setEnabled(true);
    }

    @Override
    public void hideTicketsRv() {
        binding.showIfselected.setVisibility(View.INVISIBLE);
        binding.toCartBtn.setEnabled(false);
    }

    @Override
    public void showNextView() {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), CartTimerReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+20000,pendingIntent);

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_hallTicketsFragment_to_cartFragment);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toCartBtn:
                presenter.bookTickets();

        }
    }
}
