package com.gmail.programaker.joguin.alien;

import com.gmail.programaker.joguin.earth.city.City;

import java.util.concurrent.ThreadLocalRandom;

/** Attacks a city installing a Terraform Device in it, resulting an invasion.
 * The Terraform Devices gain a random defense power to make things more interesting */
public final class AlienArmy {
    private static final int minPower = 1000;
    private static final int maxPower = 20000;

    public static Invasion attack(City city) {
        return new Invasion(new TerraformDevice(fillPower()), city);
    }

    private static int fillPower() {
        return ThreadLocalRandom.current().nextInt(minPower, maxPower + 1);
    }

    private AlienArmy(){}
}
