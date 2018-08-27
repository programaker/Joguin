package com.gmail.programaker.joguin.alien;

import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.component.TestGameComponents;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AlienArmyTest extends BaseTest {
    private CityRepository cityRepository;

    public AlienArmyTest() {
        cityRepository = new TestGameComponents().repositoryFactory().cityRepository();
    }

    @Test
    public void whenInvadingEarthCities() {
        cityRepository.findAll().forEach(city -> {
            Invasion invasion = AlienArmy.attack(city);
            TerraformDevice device = invasion.getTerraformDevice();

            int defensePower = device.getDefensePower();
            assertTrue("The invader army should have installed a Terraform Device in " + city.getName() + " with a certain defense power",
                defensePower >= 1000 && defensePower <= 20000
            );

            assertTrue("The invader army should have dominated " + city.getName(), invasion.isAlienDominatedCity());
        });
    }
}