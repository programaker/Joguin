package com.gmail.programaker.joguin.zorblax;

import com.gmail.programaker.joguin.earth.City;

import java.util.concurrent.ThreadLocalRandom;

public final class InvaderArmy {
    private static final int minPower = 1000;
    private static final int maxPower = 20000;

    public static Invasion invade(City city) {
        return new Invasion(new TerraformDevice(fillPower()), city);
    }

    private static int fillPower() {
        return ThreadLocalRandom.current().nextInt(minPower, maxPower + 1);
    }

    private InvaderArmy(){}
}
