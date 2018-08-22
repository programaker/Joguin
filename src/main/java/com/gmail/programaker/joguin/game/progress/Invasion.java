package com.gmail.programaker.joguin.game.progress;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.zorblax.TerraformDevice;

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

    public Invasion setAliensDefeated() {
        this.aliensDefeated = true;
        return this;
    }
}
