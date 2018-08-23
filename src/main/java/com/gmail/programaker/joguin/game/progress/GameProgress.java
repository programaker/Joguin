package com.gmail.programaker.joguin.game.progress;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.zorblax.Invasion;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameProgress {
    private final MainCharacter character;
    private final List<Invasion> invasions;
    private final Map<String,Invasion> invasionsByCity;
    private int characterExperience;

    public GameProgress(MainCharacter character, List<Invasion> invasions) {
        this.character = character;
        this.invasions = Collections.unmodifiableList(invasions);
        this.characterExperience = 0;

        this.invasionsByCity = this.invasions.stream()
            .collect(Collectors.toMap(
                invasion -> invasion.getLocation().getCity(),
                Function.identity()
            ));
    }

    public MainCharacter getCharacter() {
        return character;
    }

    public List<Invasion> getInvasions() {
        return invasions;
    }

    public Invasion getInvasion(Location location) {
        return invasionsByCity.get(location.getCity());
    }

    public int getCharacterExperience() {
        return characterExperience;
    }

    public GameProgress increaseCharacterExperience(int experiencePoints) {
        this.characterExperience += experiencePoints;
        return this;
    }
}
