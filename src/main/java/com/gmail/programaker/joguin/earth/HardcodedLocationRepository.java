package com.gmail.programaker.joguin.earth;

import java.util.Arrays;
import java.util.List;

public class HardcodedLocationRepository implements LocationRepository {
    private static final List<Location> allLocations = Arrays.asList(
        new Location("Berlin", "Germany"),
        new Location("Juiz de Fora", "Brazil"),
        new Location("Los Angeles", "USA"),
        new Location("Beijing", "China"),
        new Location("Nairobi", "Kenya"),
        new Location("Wellington", "New Zealand")
    );

    @Override
    public List<Location> findAll() {
        return allLocations;
    }
}
