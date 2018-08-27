package com.gmail.programaker.joguin.util;

import com.gmail.programaker.joguin.alien.AlienArmy;
import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.earth.city.City;
import com.gmail.programaker.joguin.game.progress.GameProgress;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestUtil {
    public static final Consumer<String> blackHoleConsole = message -> {};
    public static Consumer<Long> sleep = millis -> {};

    public static GameProgress beginProgress() {
        MainCharacter character = new MainCharacter("Uhura", MainCharacter.Gender.FEMALE, 36);

        List<Invasion> invasions = Arrays.asList(
            AlienArmy.attack(new City("London", "UK")),
            AlienArmy.attack(new City("Tokyo", "Japan")),
            AlienArmy.attack(new City("São Paulo", "Brazil"))
        );

        return new GameProgress(character, invasions);
    }
}
