package com.gmail.programaker.joguin.alien;

import com.gmail.programaker.joguin.earth.city.City;

import java.io.Serializable;

/** The result of an alien army attacking a city.
 *
 * After the invasion, the city is completely dominated by the aliens
 * and got a Terraform Device installed in it */
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
