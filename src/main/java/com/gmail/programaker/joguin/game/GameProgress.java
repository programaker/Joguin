package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.zorblax.Invasion;

import java.util.Collections;
import java.util.List;

public class GameProgress {
    private final MainCharacter character;
    private final List<Invasion> invasions;
    private int characterExperience;

    public GameProgress(MainCharacter character, List<Invasion> invasions) {
        this.character = character;
        this.invasions = Collections.unmodifiableList(invasions);
        this.characterExperience = 0;
    }

    public MainCharacter getCharacter() {
        return character;
    }

    public List<Invasion> getInvasions() {
        return invasions;
    }

    public Invasion getInvasion(int selectedInvasion) {
        return invasions.get(selectedInvasion - 1);
    }

    public int getCharacterExperience() {
        return characterExperience;
    }

    public GameProgress increaseCharacterExperience(int experiencePoints) {
        this.characterExperience += experiencePoints;
        return this;
    }
}
