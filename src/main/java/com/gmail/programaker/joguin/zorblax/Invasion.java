package com.gmail.programaker.joguin.zorblax;

import com.gmail.programaker.joguin.earth.Location;

public class Invasion {
    private final TerraformDevice terraformDevice;
    private final Location location;
    private boolean alienDominatedLocation;

    public Invasion(TerraformDevice terraformDevice, Location location) {
        this.terraformDevice = terraformDevice;
        this.location = location;
        this.alienDominatedLocation = true;
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

    public Invasion setAlienDominatedLocation(boolean alienDominatedLocation) {
        this.alienDominatedLocation = alienDominatedLocation;
        return this;
    }
}
