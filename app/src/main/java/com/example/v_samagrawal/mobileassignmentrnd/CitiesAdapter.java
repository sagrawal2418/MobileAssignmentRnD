package com.example.v_samagrawal.mobileassignmentrnd;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.MyViewHolder> implements Filterable {
    private List<City> filteredCities;
    private List<City> cities;
    private CitySelectionListener citySelectionListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView cityCountryTv;

        public MyViewHolder(View v) {
            super(v);
            cityCountryTv = v.findViewById(R.id.city_country_tv);
        }
    }

    public CitiesAdapter(CitySelectionListener citySelectionListener) {
        this.citySelectionListener = citySelectionListener;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
        this.filteredCities = cities;
        notifyDataSetChanged();
    }

    @Override
    public CitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item_view, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final City city = filteredCities.get(position);
        holder.cityCountryTv.setText(city.getFormattedName());
        holder.cityCountryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (citySelectionListener != null) {
                    citySelectionListener.onCitySelected(city);
                }
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredCities = cities;
                } else {
                    List<City> filteredList = new ArrayList<>();
                    for (City row : cities) {

                        if (row.getName().toLowerCase().startsWith(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredCities = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCities;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCities = (List<City>) filterResults.values;
                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return filteredCities == null ? 0 : filteredCities.size();
    }


    public interface CitySelectionListener {
        void onCitySelected(City city);
    }
}