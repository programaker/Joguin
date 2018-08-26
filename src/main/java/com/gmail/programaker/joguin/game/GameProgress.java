package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.zorblax.Invasion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private final MainCharacter character;
    private final List<Invasion> invasions;
    private int characterExperience;
    private int defeatedInvasions;

    public GameProgress(MainCharacter character, List<Invasion> invasions) {
        this.character = character;

        //The invasions can only be modified through GameProgress
        this.invasions = new ArrayList<>(invasions);

        this.characterExperience = 0;
        this.defeatedInvasions = 0;
    }

    public MainCharacter getCharacter() {
        return character;
    }

    public List<Invasion> getInvasions() {
        //The invasions can only be modified through GameProgress
        return Collections.unmodifiableList(invasions);
    }

    public Invasion getInvasion(int selectedInvasion) {
        return invasions.get(index(selectedInvasion));
    }

    public int getCharacterExperience() {
        return characterExperience;
    }

    public GameProgress increaseCharacterExperience(int experiencePoints) {
        this.characterExperience += experiencePoints;
        return this;
    }

    public GameProgress defeatInvasion(int selectedInvasion) {
        Invasion invasion = getInvasion(selectedInvasion);

        if (invasion.isAlienDominatedLocation()) {
            invasions.set(index(selectedInvasion), invasion.defeat());
            defeatedInvasions++;
        }

        return this;
    }

    public boolean allInvasionsDefeated() {
        return defeatedInvasions == invasions.size();
    }

    private int index(int selectedInvasion) {
        return selectedInvasion - 1;
    }
}
