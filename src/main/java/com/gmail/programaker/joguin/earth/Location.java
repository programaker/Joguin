package com.gmail.programaker.joguin.earth;

public class Location {
    private final String city;
    private final String country;

    public Location(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
