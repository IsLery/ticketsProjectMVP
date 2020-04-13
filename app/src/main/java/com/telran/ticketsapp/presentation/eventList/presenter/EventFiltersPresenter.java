//package com.telran.ticketsapp.presentation.eventList.presenter;
//
//import com.arellomobile.mvp.InjectViewState;
//import com.arellomobile.mvp.MvpPresenter;
//import com.telran.ticketsapp.presentation.eventList.view.filters.EventFiltersView;
//
//import java.util.List;
//
//@InjectViewState
//public class EventFiltersPresenter extends MvpPresenter<EventFiltersView> {
//    EventListPresenter listPresenter;
//   // List<Integer> selectedCategories;
//
//    public EventFiltersPresenter(EventListPresenter listPresenter) {
//        this.listPresenter = listPresenter;
//   //     selectedCategories = listPresenter.getFilterCategories();
//    }
//
//    public void getAppliedCategoryFilters(){
//        getViewState().getAppliedCategoryFilters(listPresenter.getFilterCategories());
//    }
//
//    public void getAppliedDates(){
//        getViewState().getAppliedDateFilters(listPresenter.getSelectedDates());
//    }
//
//    public void clearFilters(){
//        listPresenter.setFilterCategories(null);
//        listPresenter.setFilterDates(null,null);
//        getViewState().closeDialog();
//    }
//
//    public void setFilters(List<Integer> selectedCategories, Long startDate, Long endDate){
//        listPresenter.setFilterDates(startDate, endDate);
//        listPresenter.setFilterCategories(selectedCategories);
//    }
//
//
//}
//
