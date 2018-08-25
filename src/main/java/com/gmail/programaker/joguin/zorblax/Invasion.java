package com.gmail.programaker.joguin.zorblax;

import com.gmail.programaker.joguin.earth.Location;

public class Invasion {
    private final TerraformDevice terraformDevice;
    private final Location location;
    private boolean aliensDefeated;

    public Invasion(TerraformDevice terraformDevice, Location location) {
        this.terraformDevice = terraformDevice;
        this.location = location;
        this.aliensDefeated = false;
    }

    public TerraformDevice getTerraformDevice() {
        return terraformDevice;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isAliensDefeated() {
        return aliensDefeated;
    }

    public Invasion setAliensDefeated(boolean aliensDefeated) {
        this.aliensDefeated = aliensDefeated;
        return this;
    }
}
