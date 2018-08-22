package com.gmail.programaker.joguin.game.progress;

import com.gmail.programaker.joguin.earth.MainCharacter;

import java.util.Map;

public class GameProgress {
    private MainCharacter character;
    private Map<String,Invasion> invasionsByCity;
    private int characterExperience;

    public MainCharacter getCharacter() {
        return character;
    }

    public Map<String,Invasion> getInvasionsByCity() {
        return invasionsByCity;
    }

    public int getCharacterExperience() {
        return characterExperience;
    }

    public GameProgress increaseCharacterExperience(int experiencePoints) {
        this.characterExperience += experiencePoints;
        return this;
    }
}
