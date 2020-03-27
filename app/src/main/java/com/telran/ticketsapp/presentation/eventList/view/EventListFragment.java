package com.telran.ticketsapp.presentation.eventList.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.data.eventsList.models.EventDto;
import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class EventListFragment extends MvpAppCompatFragment implements EventListView{
    private static final int GET_FILTERS = 1;
    RecyclerView eventsRv;
    ProgressBar eventListProgress;
    Button showFiltersBtn;
    public EventListAdapter adapter;
    private boolean isLoading;
    public static final String TAG = "EventListFragment";
    private long startDate, endDate;


//TODO загрузка, подгрузка через варарг или пустые данные, чтоб были только 2 метода - на получение исходника или отфильтрованных данных

    @InjectPresenter
    EventListPresenter presenter;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: loading list");
            isLoading = true;
            presenter.getCurrentEvents();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new EventListAdapter(requireContext());

        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        eventListProgress = view.findViewById(R.id.eventListProgress);
        eventsRv = view.findViewById(R.id.eventsRv);
        showFiltersBtn = view.findViewById(R.id.showFiltersBtn);
        showFiltersBtn.setOnClickListener(v -> {
            EventsFiltersDialog dialog = new EventsFiltersDialog(presenter);
            dialog.setTargetFragment(EventListFragment.this, GET_FILTERS);
            dialog.show(getFragmentManager(),null);

        });





        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        eventsRv.setLayoutManager(layoutManager);
        eventsRv.setAdapter(adapter);

        eventsRv.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = layoutManager.getItemCount();
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
                int lastVisiblePos = layoutManager.findLastVisibleItemPosition();
                if (total > 0 && lastVisiblePos == total-1 && !isLoading){
                    Log.d(TAG, "onScrolled: loading list");
                    if (startDate == 0 && endDate == 0) {
                        presenter.loadmore();
                    }else {
                        presenter.loadMoreInRange(startDate,endDate);
                    }
                    isLoading = true;
                }

            }
        });

        return view;
    }


    @Override
    public void showProgress() {
        eventsRv.setEnabled(false);
        eventListProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        eventListProgress.setVisibility(View.GONE);
        eventsRv.setEnabled(true);

    }

    @Override
    public void showEvent() {

    }

    @Override
    public void updateAll(List<EventDto> eventDtos) {
        isLoading = false;
        adapter.setEvents(eventDtos);
    }

    @Override
    public void loadMoreData(List<EventDto> eventDtos) {
        isLoading = false;
        adapter.updateEvents(eventDtos);
    }

    @Override
    public void showError(String m){
        new AlertDialog.Builder(getContext())
                .setTitle("Error")
                .setMessage(m)
                .setCancelable(false)
                .setPositiveButton("OK",null)
                .create()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == GET_FILTERS && data != null){
                ArrayList<Integer> catgs = data.getIntegerArrayListExtra(EventsFiltersDialog.CATEGORIES);
                Log.d(TAG, "onActivityResult: "+catgs.size());
                startDate = data.getLongExtra(EventsFiltersDialog.START_DATE,0);
                endDate = data.getLongExtra(EventsFiltersDialog.END_DATE,0);

                // если был фильтр и пропал - загружаем текущие значения заново
                // если тупо ничего не выбрано - оставляем как есть
                presenter.getEventsInRange(startDate, endDate);
                presenter.filterByCategory(catgs);
            }
        }
    }
}
