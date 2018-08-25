package com.gmail.programaker.joguin.zorblax;

import com.gmail.programaker.joguin.earth.Location;

import java.util.concurrent.ThreadLocalRandom;

public final class InvaderArmy {
    private static final int minPower = 1000;
    private static final int maxPower = 20000;

    public static Invasion invade(Location location) {
        return new Invasion(new TerraformDevice(fillPower()), location);
    }

    private static int fillPower() {
        return ThreadLocalRandom.current().nextInt(minPower, maxPower + 1);
    }

    private InvaderArmy(){}
}
