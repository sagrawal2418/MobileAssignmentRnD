package com.example.v_samagrawal.mobileassignmentrnd;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("country")
    private String country;

    @SerializedName("name")
    private String name;

    @SerializedName("_id")
    private int id;

    @SerializedName("coord")
    private Coordinate coordinate;

    public City(String country, String name, int id, Coordinate coordinate) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.coordinate = coordinate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getFormattedName() {
        return name + ", " + country;
    }

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", coordinate=" + coordinate +
                '}';
    }
}
