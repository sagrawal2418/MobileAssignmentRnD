package com.example.v_samagrawal.mobileassignmentrnd;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

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

public class JsonParserTask extends AsyncTask<Void, Void, List<City>> {
    private final String TAG = JsonParserTask.class.getSimpleName();

    private ParserCallback callback;

    private final Gson serializer = new Gson();
    private final Type CITY_TYPE = new TypeToken<Collection<City>>() {
    }.getType();

    public JsonParserTask(@NonNull ParserCallback callback) {
        this.callback = callback;
    }

    protected List<City> doInBackground(Void... input) {
        List<City> cities = getCitiesList();
        Collections.sort(cities, new CityComparator());
        return cities;
    }

    protected void onPostExecute(List<City> result) {
        callback.onResult(result);
    }

    private List<City> getCitiesList() {
        List<City> list = null;
        long start = System.currentTimeMillis();
        Log.d(TAG, "JSON parsing started at " + start);
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = callback.getActivity().getAssets().open("cities.json");
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            list = serializer.fromJson(sb.toString(), CITY_TYPE);
            /*for(int i=0; i<200000; i++) {
                list.add(new City(getRandom(2).toUpperCase(), getRandom(15), i, null));
            }*/
            Log.d(TAG, "JSON parsing took " + (System.currentTimeMillis()-start) +" millis");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /*private Random r = new Random();
    private String getRandom(int len) {
        StringBuffer sb = new StringBuffer(len);
        for(int i=0; i<len; i++) {
            sb.append((char) (97 + r.nextInt(52)));
        }
        return sb.toString();
    }*/

    public interface ParserCallback {
        void onResult(List<City> result);
        @NonNull Activity getActivity();
    }
}


