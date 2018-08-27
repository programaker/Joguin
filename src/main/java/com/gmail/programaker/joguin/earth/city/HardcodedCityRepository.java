package com.gmail.programaker.joguin.earth.city;

import java.util.Arrays;
import java.util.List;

/** CityRepository implementation that uses a simple List to store the cities */
public class HardcodedCityRepository implements CityRepository {
    private static final List<City> ALL_CITIES = Arrays.asList(
        new City("Berlin", "Germany"),
        new City("Juiz de Fora", "Brazil"),
        new City("Los Angeles", "USA"),
        new City("Beijing", "China"),
        new City("Nairobi", "Kenya"),
        new City("Wellington", "New Zealand")
    );

    @Override
    public List<City> findAll() {
        return ALL_CITIES;
    }
}
