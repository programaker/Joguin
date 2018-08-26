package com.gmail.programaker.joguin.alien;

import com.gmail.programaker.joguin.earth.city.City;

import java.io.Serializable;

public class Invasion implements Serializable {
    private static final long serialVersionUID = 1L;
    private final TerraformDevice terraformDevice;
    private final City city;
    private final boolean alienDominatedCity;

    public Invasion(TerraformDevice terraformDevice, City city) {
        this(terraformDevice, city, true);
    }

    public TerraformDevice getTerraformDevice() {
        return terraformDevice;
    }

    public City getCity() {
        return city;
    }

    public boolean isAlienDominatedCity() {
        return alienDominatedCity;
    }

    public Invasion defeated() {
        return new Invasion(terraformDevice, city, false);
    }

    private Invasion(TerraformDevice terraformDevice, City city, boolean alienDominatedCity) {
        this.terraformDevice = terraformDevice;
        this.city = city;
        this.alienDominatedCity = alienDominatedCity;
    }
}
