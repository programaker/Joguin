package com.gmail.programaker.joguin.alien;

import com.gmail.programaker.joguin.earth.city.City;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class InvasionTest extends BaseTest {
    @Test
    public void whenDefeated() {
        Invasion invasion = AlienArmy.attack(new City("Osasco", "Brazil"));
        Invasion defeatedInvasion = invasion.defeated();

        assertTrue("An Invasion completelly dominates a city", invasion.isAlienDominatedCity());
        assertFalse("When defeated, the city is free", defeatedInvasion.isAlienDominatedCity());
    }
}