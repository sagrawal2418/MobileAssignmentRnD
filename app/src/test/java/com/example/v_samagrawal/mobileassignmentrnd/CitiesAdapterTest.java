package com.example.v_samagrawal.mobileassignmentrnd;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CitiesAdapterTest {

    @Test
    public void filter_isCorrect() {
        List<City> list = new ArrayList<>(6);
        list.add(new City("US", "Alabama", 0, null));
        list.add(new City("US", "Albuquerque", 0, null));
        list.add(new City("US", "Anaheim", 0, null));
        list.add(new City("US", "Arizona", 0, null));
        list.add(new City("AU", "Sydney", 0, null));
        list.add(new City("UA", "Hurzuf", 0, null));

        CitiesAdapter adapter = new CitiesAdapter(list,null);
        assertEquals(6, adapter.getItemCount());

        List<City> filteredCities = adapter.filterCities(list, "");
        assertEquals(6, filteredCities.size());

        filteredCities = adapter.filterCities(list, "Syd");
        assertEquals(1, filteredCities.size());
        assertEquals("Sydney", filteredCities.get(0).getName());

        filteredCities = adapter.filterCities(list, "H");
        assertEquals(1, filteredCities.size());
        assertEquals("Hurzuf", filteredCities.get(0).getName());

        filteredCities = adapter.filterCities(list, "Al");
        assertEquals(2, filteredCities.size());
        assertEquals("Alabama", filteredCities.get(0).getName());
        assertEquals("Albuquerque", filteredCities.get(1).getName());

        filteredCities = adapter.filterCities(list, "*&^**&");
        assertEquals(0, filteredCities.size());
    }
}