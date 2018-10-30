package com.example.v_samagrawal.mobileassignmentrnd;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        new JsonParserTask(new JsonParserTask.ParserCallback() {
            @Override
            public void onResult(List<City> result) {
                showListFragment(result);
            }

            @NonNull
            @Override
            public Activity getActivity() {
                return MainActivity.this;
            }
        }).execute();

    }

    private MainListFragment mainListFragment;

    private void showListFragment(final List<City> citiesList) {
        mainListFragment = MainListFragment.newInstance(citiesList, new CitiesAdapter.CitySelectionListener() {
            @Override
            public void onCitySelected(City city) {
                showLocationFragment(city);
            }
        });
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayout, mainListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showLocationFragment(City city) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, LocationFragment.newInstance(city));
        transaction.addToBackStack(LocationFragment.class.getCanonicalName());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (mainListFragment != null && mainListFragment.onBackPressed() && mainListFragment.isVisible()) {
            return;
        }
        super.onBackPressed();
    }
}
