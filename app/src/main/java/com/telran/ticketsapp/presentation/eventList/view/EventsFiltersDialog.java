package com.telran.ticketsapp.presentation.eventList.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import com.squareup.timessquare.CalendarPickerView;
import com.telran.ticketsapp.R;
import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class EventsFiltersDialog extends BottomSheetDialogFragment {
    public static final String CATEGORIES = "Selected categories" ;
    private static final String TAG = "EventsFiltersDialog";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    private static final String PICK_DATES = "pick_dates";
    private static final int SELECT_DATES = 10;
    @BindView(R.id.events_filter)
    LinearLayout eventsFilter;

    @BindView(R.id.categoriesContainer)
    LinearLayout   categoriesContainer;

    @BindView(R.id.eventCategories)
    CheckedTextView eventCategories;

    @BindView(R.id.showCalender)
    CheckedTextView showCalender;

    @BindView(R.id.exhibitionsTxt)
    CheckedTextView exhibitionsTxt;

    @BindView(R.id.concertsTxt)
    CheckedTextView concertsTxt;

    @BindView(R.id.showsTxt)
    CheckedTextView showsTxt;

    @BindView(R.id.hideFilters)
    Button hideFilters;

    @BindView(R.id.eventCalendarView)
    CalendarPickerView eventCalendarView;

//    @BindView(R.id.pickDates)
//    DatePicker pickDates;

    Unbinder unbinder;
    EventListPresenter presenter;
    private ArrayList<Integer> selectedCategoties = new ArrayList<>();
    private ArrayList<Date> selectedDates = new ArrayList<>();


    public EventsFiltersDialog(EventListPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.events_filter,container,false);
        unbinder = ButterKnife.bind(this,view);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        Date today = new Date();
        eventCalendarView.init(today,nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE);

        eventCalendarView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
            selectedDates.add(date);
            }

            @Override
            public void onDateUnselected(Date date) {
            selectedDates.remove(date);
            }
        });

        View.OnClickListener categoriesListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckedTextView catView = (CheckedTextView) v;
                catView.setChecked(!catView.isChecked());
                Integer tag = Integer.parseInt(catView.getTag().toString());
                if (catView.isChecked()){
                    Toast.makeText(requireContext(), catView.getTag().toString(), Toast.LENGTH_SHORT).show();
                    selectedCategoties.add(tag);
                }else {
                    selectedCategoties.remove(tag);
                }
            }
        };

        showsTxt.setOnClickListener(categoriesListener);
        concertsTxt.setOnClickListener(categoriesListener);
        exhibitionsTxt.setOnClickListener(categoriesListener);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.eventCategories)
    void changeCategVisibility(){
    if (categoriesContainer.getVisibility() == View.VISIBLE)
    {
        categoriesContainer.setVisibility(View.GONE);}
    else{
        categoriesContainer.setVisibility(View.VISIBLE);
    }
    }

    @OnClick(R.id.showCalender)
    void changeCalenderVisibility(){
        if (eventCalendarView.getVisibility() == View.GONE) {
            eventCalendarView.setVisibility(View.VISIBLE);
        }else {
            eventCalendarView.setVisibility(View.GONE);
        }
//        Pair<Long, Long> selected;
//       Calendar cld = Calendar.getInstance();
//        MaterialDatePicker eventDatesPicker = MaterialDatePicker.Builder.dateRangePicker()
//                .setTitleText("Select date range")
//                .setSelection(Pair.create(cld.getTimeInMillis(),cld.getTimeInMillis()))
//                .build();
//        eventDatesPicker.addOnPositiveButtonClickListener(
//                (MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
//                    if (getTargetFragment() == null){
//                        return;
//                    }
//                    Intent intent = new Intent();
//                    intent.putExtra(START_DATE,selection.first);
//                    intent.putExtra(END_DATE,selection.second);
//                    getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);
//                }
//        );
//        eventDatesPicker.setTargetFragment(this,SELECT_DATES);
//        eventDatesPicker.show(getChildFragmentManager(),PICK_DATES);
       // Pair<Long, Long> range = (Pair<Long, Long>) eventDatesPicker.getSelection();

//        if (pickDates.getVisibility() == View.VISIBLE){
//            pickDates.setVisibility(View.GONE);
//        }else {
//            pickDates.setVisibility(View.VISIBLE);
//        }
    }

    @OnClick(R.id.hideFilters)
    void endDialog(){
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

    sendResult();

    }

    private void sendResult(){
    if (getTargetFragment() == null){
        return;
    }
    long start = 0, end = 0;
    Intent intent = new Intent();
        Log.d(TAG, "sendResult: "+selectedCategoties.size());
        intent.putIntegerArrayListExtra(CATEGORIES,selectedCategoties);
        if (selectedDates.size() == 1){
            start = selectedDates.get(0).getTime();
            end = selectedDates.get(0).getTime() + TimeUnit.HOURS.toMillis(23);
        }else if (selectedDates.size() == 2){
            start = selectedDates.get(0).getTime();
            end = selectedDates.get(1).getTime();
            if (start > end){
                long sw = start;
                start = end;
                end = start;
            }
        }
        Log.d(TAG, "sendResult: startdate" + start);
        Log.d(TAG, "sendResult: enddate " + end);
        intent.putExtra(START_DATE,start);
        intent.putExtra(END_DATE,end);

    getTargetFragment().onActivityResult(getTargetRequestCode(),RESULT_OK,intent);
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
