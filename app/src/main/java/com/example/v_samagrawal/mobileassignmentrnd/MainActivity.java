package com.example.v_samagrawal.mobileassignmentrnd;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final Gson serializer = new Gson();
    private static final Type CITY_TYPE = new TypeToken<Collection<City>>() {
    }.getType();
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        new JsonParserTask(this).execute();

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


    private static class JsonParserTask extends AsyncTask<Void, Void, List<City>> {
        private MainActivity activity;


        JsonParserTask(MainActivity activity) {
            this.activity = activity;
        }

        protected List<City> doInBackground(Void... input) {
            List<City> cities = getCitiesList();
            Collections.sort(cities, new CustomComparator());
            return cities;
        }

        protected void onPostExecute(List<City> result) {
            activity.showListFragment(result);
        }

        private List<City> getCitiesList() {
            String json = "[]";
            try {
                StringBuilder buf = new StringBuilder();
                InputStream is = activity.getAssets().open("cities.json");
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String str;

                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }

                in.close();
                json = buf.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return serializer.fromJson(json, CITY_TYPE);
        }
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
