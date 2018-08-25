package com.gmail.programaker.joguin.zorblax;

import com.gmail.programaker.joguin.earth.Location;

public class Invasion {
    private final TerraformDevice terraformDevice;
    private final Location location;
    private final boolean alienDominatedLocation;

    public Invasion(TerraformDevice terraformDevice, Location location) {
        this(terraformDevice, location, true);
    }

    public TerraformDevice getTerraformDevice() {
        return terraformDevice;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAlienDominatedLocation() {
        return alienDominatedLocation;
    }

    public Invasion defeat() {
        return new Invasion(terraformDevice, location, false);
    }

    private Invasion(TerraformDevice terraformDevice, Location location, boolean alienDominatedLocation) {
        this.terraformDevice = terraformDevice;
        this.location = location;
        this.alienDominatedLocation = alienDominatedLocation;
    }
}
