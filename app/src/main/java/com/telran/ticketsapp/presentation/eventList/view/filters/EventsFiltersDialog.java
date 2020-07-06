package com.telran.ticketsapp.presentation.eventList.view.filters;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckedTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import com.google.android.material.navigation.NavigationView;
import com.squareup.timessquare.CalendarPickerView;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.databinding.EventsFilterBinding;
import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;
import com.telran.ticketsapp.presentation.eventList.view.EventListFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import javax.inject.Inject;


import static android.app.Activity.RESULT_OK;

public class EventsFiltersDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String CATEGORIES = "Selected categories" ;
    private static final String INIT_CATEGS = "initial categories";
    private static final String TAG = "EventsFiltersDialog";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    private static final String PICK_DATES = "pick_dates";
    private static final int SELECT_DATES = 10;



    EventsFilterBinding binding;
   // private ArrayList<Integer> selectedCategories;
    private List<Integer> selectedCategories;
    private ArrayList<CheckedTextView> categs;
    private ArrayList<Date> selectedDates;
    private EventListPresenter presenter;

    public EventsFiltersDialog() {
    }


    public static EventsFiltersDialog getInstance(ArrayList<Integer> selected){
        Bundle args = new Bundle();
        args.putIntegerArrayList(INIT_CATEGS,selected);
        EventsFiltersDialog dialog = new EventsFiltersDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // presenter = fragment.getListPresenter();
     //   selectedCategories = presenter.getFilterCategories();
        selectedCategories = new ArrayList<>(3);
        categs = new ArrayList<>();
       selectedDates = new ArrayList<>(2);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EventsFilterBinding.inflate(inflater,container,false);
        categs.add(binding.showsTxt);
        categs.add(binding.concertsTxt);
        categs.add(binding.exhibitionsTxt);

        for (CheckedTextView v: categs) {
                Log.d(TAG, "onCreateView: categs"+ v.getText());
                if (selectedCategories.size() > 0){
                    initiallySelected(v);
                }
                v.setOnClickListener(this);
        }
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        binding.eventCalendarView.init(today,nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);
        if (selectedDates.size()>1) {

            binding.eventCalendarView.selectDate(selectedDates.get(0));
            binding.eventCalendarView.selectDate(selectedDates.get(1));
        }
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
        binding.hideFilters.setOnClickListener(this);
        binding.eventCategories.setOnClickListener(this);
        binding.showCalender.setOnClickListener(this);
        binding.clearFilters.setOnClickListener(this);
        binding.selectFilters.setOnClickListener(this);

        return binding.getRoot();
    }


    private void changeCategVisibility(){
    if (binding.categoriesContainer.getVisibility() == View.VISIBLE)
    {
        binding.categoriesContainer.setVisibility(View.GONE);}
    else{
        binding.categoriesContainer.setVisibility(View.VISIBLE);
    }
    }


    private void changeCalenderVisibility(){
        if (binding.eventCalendarView.getVisibility() == View.GONE) {
            binding.eventCalendarView.setVisibility(View.VISIBLE);
        }else {
            binding.eventCalendarView.setVisibility(View.GONE);
        }
    }

    private void sendResult(){
//        long start = selectedDates.size() >= 1 ? selectedDates.get(0).getTime() : 0;
//        long end = selectedDates.size() >= 2 ? selectedDates.get(1).getTime() : 0;
//        presenter.setFilterDates(start,end);
//        presenter.setFilterCategories(selectedCategories);

    if (getTargetFragment() == null){
        return;
    }

    Intent intent = new Intent();

        Log.d(TAG, "sendResult: "+ selectedCategories.size());
        Long start = selectedDates.size() >= 1 ? selectedDates.get(0).getTime() : null;
        Long end = selectedDates.size() >= 2 ? selectedDates.get(1).getTime() : null;
        Log.d(TAG, "sendResult: startdate" + start);
        Log.d(TAG, "sendResult: enddate " + end);
        intent.putIntegerArrayListExtra(CATEGORIES,(ArrayList<Integer>)selectedCategories);
        intent.putExtra(START_DATE, start);
        intent.putExtra(END_DATE, end);
        getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);

     //   presenter.closeFilters();
    }


    private void clearFilters(){
        if (getTargetFragment() == null){
            return;
        }
        for (CheckedTextView v: categs
             ) {
            v.setChecked(false);
        }
        selectedCategories.clear();
//        presenter.setFilterDates(0,0);
//        presenter.setFilterCategories(null);
      //  Navigation.findNavController(binding.getRoot()).popBackStack();
        Intent intent = new Intent();

        intent.putIntegerArrayListExtra(CATEGORIES,null);
        intent.putExtra(START_DATE, 0);
        intent.putExtra(END_DATE, 0);
        Log.d(TAG, "clearFilters: ");

        getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);


    }


    private void selectCategory(  CheckedTextView catView){
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
                return;
            case R.id.hideFilters:
              //  getActivity().onBackPressed();
             //   Navigation.findNavController(binding.getRoot()).popBackStack();
             //   dismiss();
                dismiss();
                return;
            case R.id.clearFilters:
                clearFilters();
                dismiss();
               // getActivity().onBackPressed();
                return;
            case R.id.selectFilters:
                sendResult();
                dismiss();
             //   getChildFragmentManager().popBackStack();
             //   getActivity().onBackPressed();
                return;
            case R.id.eventCategories:
                changeCategVisibility();
                return;
            case R.id.showCalender:
                changeCalenderVisibility();

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
