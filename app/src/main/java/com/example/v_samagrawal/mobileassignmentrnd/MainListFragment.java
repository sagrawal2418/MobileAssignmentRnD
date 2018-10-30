package com.example.v_samagrawal.mobileassignmentrnd;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainListFragment extends Fragment {
    private final String TAG = MainListFragment.class.getSimpleName();

    private SearchView searchView;

    private CitiesAdapter citiesAdapter;
    private CitiesAdapter.CitySelectionListener citySelectionListener;
    private List<City> cities;

    private Handler handler = new Handler();

    public static MainListFragment newInstance(List<City> cities, CitiesAdapter.CitySelectionListener citySelectionListener) {
        MainListFragment fragment = new MainListFragment();
        fragment.cities = cities;
        fragment.citySelectionListener = citySelectionListener;
        return fragment;
    }

    public MainListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                submitDelayedQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                submitDelayedQuery(query);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        citiesAdapter = new CitiesAdapter(cities, citySelectionListener);
        recyclerView.setAdapter(citiesAdapter);
    }

    private void submitDelayedQuery(final String query) {
        Log.d(TAG, "received query=" + query);
        handler.removeCallbacksAndMessages(null);
        /*
        Execute delayed so previous query gets completed.
        Our tests show that filtering 200k records taking around 350 ms on higher end devices
         */
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                citiesAdapter.getFilter().filter(query);
                Log.d(TAG, "executed query=" + query);
            }
        }, 400);
    }

    public boolean onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return true;
        }
        return false;
    }
}
