package com.telran.ticketsapp.presentation.eventList.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.telran.ticketsapp.R;

import com.telran.ticketsapp.databinding.FragmentEventListBinding;
import com.telran.ticketsapp.presentation.eventList.presenter.EventListPresenter;
import com.telran.ticketsapp.presentation.eventList.view.filters.EventsFiltersDialog;
import com.telran.ticketsapp.presentation.eventpage.view.EventFragment;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class EventListFragment extends MvpAppCompatFragment implements EventListView, View.OnClickListener {
    private static final int GET_FILTERS = 1;
    private FragmentEventListBinding binding;


    private EventListAdapter adapter;
    private boolean isLoading;
    private LinearLayoutManager layoutManager;
    private RecyclerView.OnScrollListener scrollListener;
    private EventsFiltersDialog filtersDialog;

    public static final String TAG = "EventListFragment";



    @InjectPresenter
    EventListPresenter presenter;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        setRetainInstance(true);
  //      presenter.getEvents();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.eventlist_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.item_eventSearch);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: "+query);
                presenter.setSearchQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
            
        });

        View closeBtn = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeBtn.setOnClickListener(v -> {
                    presenter.setSearchQuery("");
                    searchView.setQuery("",false);
            Log.d(TAG, "onCreateOptionsMenu: clear search");}
        );
        searchView.setOnSearchClickListener(v -> searchView.setQuery(presenter.getSearchQuery(),false));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventListBinding.inflate(inflater,container,false);
        adapter = new EventListAdapter(requireContext());


        Objects.requireNonNull(getActivity()).setTitle("Events:");

        binding.showFiltersBtn.setOnClickListener(this);
        Log.d(TAG, "onCreateView: ");
        layoutManager = new LinearLayoutManager(requireContext());
        binding.eventsRv.setLayoutManager(layoutManager);
        adapter = presenter.getAdapterInstance();
        binding.eventsRv.setAdapter(adapter);
        adapter.setOnEventClickListener(((adapter1, position) -> {
                    EventFragment frag = EventFragment.newInstance(adapter.getEventId(position));
                    requireFragmentManager().beginTransaction().replace(R.id.root,frag)
                            .commit();
        }));
        return binding.getRoot();
    }



    @Override
    public void setScrollListener(){
        Log.d(TAG, "setScrollListener: ");
        if (scrollListener == null) {
            scrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dx != 0 || dy != 0) {
                        int total = layoutManager.getItemCount();
                        int lastVisiblePos = layoutManager.findLastVisibleItemPosition();
                        if (total > 0 && lastVisiblePos == total - 1 && !isLoading) {
                            Log.d(TAG, "onScrolled: loading list");
                            isLoading = true;
                            presenter.loadmore();

                        }
                    }

                }
            };
        }
        binding.eventsRv.addOnScrollListener(scrollListener);
    }

    @Override
    public void removeScrollListener(){
        Log.d(TAG, "removeScrollListener: ");
        binding.eventsRv.removeOnScrollListener(scrollListener);
    }


    @Override
    public void showFiltersDialog() {
        filtersDialog = new EventsFiltersDialog(presenter);
        filtersDialog.setTargetFragment(EventListFragment.this, GET_FILTERS);
        filtersDialog.show(getFragmentManager(),null);
    }


    @Override
    public void showProgress() {
        binding.eventsRv.setEnabled(false);
        binding.eventListProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.eventListProgress.setVisibility(View.GONE);
        binding.eventsRv.setEnabled(true);

    }

    @Override
    public void showEvent() {

    }

    @Override
    public void isLoadingData(boolean stillLoading) {
        Log.d(TAG, "isLoadingData: "+stillLoading);
        isLoading = stillLoading;
    }

    @Override
    public void showError(String m){
        isLoading = false;
        new AlertDialog.Builder(requireContext())
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
            if (requestCode == GET_FILTERS){
                Log.d(TAG, "onActivityResult: ");
                presenter.getEvents();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showFiltersBtn:
                presenter.openFilters();

        }
    }


}
