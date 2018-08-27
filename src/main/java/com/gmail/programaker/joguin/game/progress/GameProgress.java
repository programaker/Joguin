package com.gmail.programaker.joguin.game.progress;

import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.earth.MainCharacter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Represents the player's progress through the game,
 * the gained experience, saved cities, etc */
public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private final MainCharacter character;
    private final List<Invasion> invasions;

    //The experience is not in the MainCharacter to enable
    //the possibility of reuse the same character in a new game,
    //with 0 experience, and at the same time resume a game with
    //the same character, more experienced
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
        //1-based index, to match the invasion list as the player sees it
        //and also the player's input when select an invasion to fight
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

        if (invasion.isAlienDominatedCity()) {
            //Invasion is immutable to forbid changes outside GameProgress
            //So an invasion must be replaced by its defeated counterpart
            invasions.set(index(selectedInvasion), invasion.defeated());
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
