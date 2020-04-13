package com.telran.ticketsapp.presentation.eventList.view.filters;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckedTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import com.squareup.timessquare.CalendarPickerView;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.databinding.EventsFilterBinding;
import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class EventsFiltersDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String CATEGORIES = "Selected categories" ;
    private static final String TAG = "EventsFiltersDialog";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    private static final String PICK_DATES = "pick_dates";
    private static final int SELECT_DATES = 10;



    Unbinder unbinder;

    EventsFilterBinding binding;
    EventListPresenter presenter;
    private List<Integer> selectedCategories;
    private ArrayList<Date> selectedDates = new ArrayList<>(2);

    public EventsFiltersDialog() {
    }

    public EventsFiltersDialog(EventListPresenter presenter) {
//        this.presenter = presenter;
//        selectedCategories = presenter.getFilterCategories();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventsFilterBinding.inflate(inflater,container,false);

        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        binding.eventCalendarView.init(today,nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);

        binding.eventCalendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
            selectedDates.add(date);
            }

            @Override
            public void onDateUnselected(Date date) {
            selectedDates.remove(date);
            }
        });

        initiallySelected(binding.showsTxt);
        initiallySelected(binding.concertsTxt);
        initiallySelected(binding.exhibitionsTxt);


        binding.hideFilters.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    void changeCategVisibility(){
    if (binding.categoriesContainer.getVisibility() == View.VISIBLE)
    {
        binding.categoriesContainer.setVisibility(View.GONE);}
    else{
        binding.categoriesContainer.setVisibility(View.VISIBLE);
    }
    }


    void changeCalenderVisibility(){
        if (binding.eventCalendarView.getVisibility() == View.GONE) {
            binding.eventCalendarView.setVisibility(View.VISIBLE);
        }else {
            binding.eventCalendarView.setVisibility(View.GONE);
        }
    }


    void endDialog(){
        dismiss();
    }



    public void sendResult(){
    if (getTargetFragment() == null){
        return;
    }

    Intent intent = new Intent();
        Log.d(TAG, "sendResult: "+ selectedCategories.size());
        presenter.setFilterCategories(selectedCategories);
        Long start = selectedDates.size() >= 1 ? selectedDates.get(0).getTime() : null;
        Long end = selectedDates.size() >= 2 ? selectedDates.get(1).getTime() : null;
        Log.d(TAG, "sendResult: startdate" + start);
        Log.d(TAG, "sendResult: enddate " + end);
        presenter.setFilterDates(start, end);
   //     getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);
     //   presenter.closeFilters();
    }


    public void clearFilters(){
        presenter.setFilterDates(null, null);
        presenter.setFilterCategories(null);

    }


    public void selectCategory(  CheckedTextView catView){
        catView.setChecked(!catView.isChecked());
        Integer tag = Integer.parseInt(catView.getTag().toString());
        if (catView.isChecked()){
            Toast.makeText(requireContext(), catView.getTag().toString(), Toast.LENGTH_SHORT).show();
            selectedCategories.add(tag);
        }else {
            selectedCategories.remove(tag);
        }
    }

    private void initiallySelected(CheckedTextView catView){
        Integer tag = Integer.parseInt(catView.getTag().toString());
        if (selectedCategories.contains(tag)){
            catView.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exhibitionsTxt:
            case R.id.concertsTxt:
            case R.id.showsTxt:
                selectCategory((CheckedTextView) v);
                break;
            case R.id.hideFilters:
                dismiss();
                break;
            case R.id.clearFilters:
                clearFilters();
                break;
            case R.id.selectFilters:
                sendResult();
                break;
            case R.id.eventCategories:
                changeCategVisibility();
                break;
            case R.id.showCalender:
                changeCalenderVisibility();
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            if (requestCode == SELECT_DATES && data != null){
//                long start = data.getLongExtra(START_DATE,0);
//                long end = data.getLongExtra(END_DATE,0);
//                presenter.getEventsInRange(start, end);
//            }
//        }
//    }
}
