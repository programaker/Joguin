package com.gmail.programaker.joguin.util;

import com.gmail.programaker.joguin.earth.City;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.game.GameProgress;
import com.gmail.programaker.joguin.zorblax.InvaderArmy;
import com.gmail.programaker.joguin.zorblax.Invasion;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestUtil {
    public static final Consumer<String> blackHoleConsole = message -> {};
    public static Consumer<Long> sleep = millis -> {};

    public static GameProgress beginProgress() {
        MainCharacter character = new MainCharacter("Uhura", MainCharacter.Gender.FEMALE, 36);

        List<Invasion> invasions = Arrays.asList(
            InvaderArmy.invade(new City("London", "UK")),
            InvaderArmy.invade(new City("Tokyo", "Japan")),
            InvaderArmy.invade(new City("São Paulo", "Brazil"))
        );

        return new GameProgress(character, invasions);
    }
}
