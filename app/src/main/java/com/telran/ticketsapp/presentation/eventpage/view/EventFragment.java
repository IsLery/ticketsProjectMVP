package com.telran.ticketsapp.presentation.eventpage.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.squareup.picasso.Picasso;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.eventPage.model.UIEvent;
import com.telran.ticketsapp.data.eventsList.dto.EventDto;
import com.telran.ticketsapp.data.utils.DataFormatMethods;
import com.telran.ticketsapp.databinding.EventRowBinding;
import com.telran.ticketsapp.databinding.FragmentEventBinding;
import com.telran.ticketsapp.presentation.eventpage.presenter.EventPresenter;
import com.telran.ticketsapp.presentation.tickets.view.HallTicketsFragment;


public class EventFragment extends MvpAppCompatFragment implements EventView {

    private static final String EVENT_ID = "event_ID";
    FragmentEventBinding binding;
    EventRowBinding rowBinding;



    @InjectPresenter
    public EventPresenter presenter;



    public static EventFragment newInstance(String eventId) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    public static Bundle eventFragmentBundle(String eventId){
        Bundle args = new Bundle();
        args.putString(EVENT_ID, eventId);
        return args;
    }

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String eventId = getArguments().getString(EVENT_ID);
            presenter.getEvent(eventId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater,container,false);
        rowBinding = binding.includeRow;
        binding.buyTicketsBtn.setOnClickListener(v -> presenter.toNextView());

        return binding.getRoot();
    }

    @Override
    public void showProgress() {
        binding.progressRegBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressRegBar.setVisibility(View.GONE);
    }

    @Override
    public void showNextView(UIEvent event) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_eventFragment_to_hallTicketsFragment,
                HallTicketsFragment.makeBundleForHallTicketsFragment(
                        event.getEventId(),event.getHall(),event.getArtist(),
                        event.getEventName(),
                        DataFormatMethods.getDateTxt("dd MMMM yyy",event.getEventStart())));
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void setEventInfo(UIEvent event) {
        rowBinding.eventTitleTxt.setText(event.getEventName());
        rowBinding.artistTxt.setText(event.getArtist());
        binding.eventDescription.setText(event.getDescription());
        binding.priceInfo.setText(getString(R.string.price_range_event,event.getMinPrice(),event.getMaxPrice()));
        binding.dateInfo.setText(DataFormatMethods.getDateTxt("dd MMMM yyy, hh:mm",event.getEventStart()));
        binding.ticketsInfo.setText(event.getRestTick()+"");
        Picasso.get().load(event.getImages().get(0))
                .into(rowBinding.eventImage);
    }
}
