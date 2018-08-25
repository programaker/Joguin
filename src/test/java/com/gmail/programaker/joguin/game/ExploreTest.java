package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.util.BaseTest;
import com.gmail.programaker.joguin.zorblax.InvaderArmy;
import com.gmail.programaker.joguin.zorblax.Invasion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.config.TestConfig.blackHoleConsole;
import static org.junit.Assert.*;

public class ExploreTest extends BaseTest {
    private final String london = "1. \uD83D\uDC7D London - UK";
    private final String tokyo = "2. \uD83D\uDC7D Tokyo - Japan";
    private final String saoPaulo = "3. \uD83D\uDC7D São Paulo - Brazil";
    private final String savedSaoPaulo = "3. \uD83C\uDF0D São Paulo - Brazil";
    private final String whereToGo = "\nWhere do you want to go? - from 1 to 3, (Q)uit:";
    private final String invalidOption = "Invalid option";

    @Autowired
    private Explore explore;

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        explore.start(beginProgress()).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("3").iterator()
        );

        int i = 0;
        assertEquals("", fakeConsole.get(i++));
        assertEquals(london, fakeConsole.get(i++));
        assertEquals(tokyo, fakeConsole.get(i++));
        assertEquals(saoPaulo, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));
    }

    @Test
    public void interactionsWithPlayerAfterSavingCity() {
        List<String> fakeConsole = new ArrayList<>();

        explore.start(saveSaoPaulo(beginProgress())).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("3").iterator()
        );

        int i = 0;
        assertEquals("", fakeConsole.get(i++));
        assertEquals(london, fakeConsole.get(i++));
        assertEquals(tokyo, fakeConsole.get(i++));
        assertEquals(savedSaoPaulo, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));
    }

    @Test
    public void whenThePlayerAsksToQuit() {
        GameStep nextStep = explore.start(beginProgress()).interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertEquals("Should have gone to Quit step", "Quit", nextStep.name());
    }

    @Test
    public void givenWrongOption() {
        List<String> fakeConsole = new ArrayList<>();

        explore.start(beginProgress()).interactWithPlayer(
            fakeConsole::add,
            Arrays.asList("0", "a", " ", "9", "1").iterator()
        );

        int i = 0;

        assertEquals("", fakeConsole.get(i++));
        assertEquals(london, fakeConsole.get(i++));
        assertEquals(tokyo, fakeConsole.get(i++));
        assertEquals(saoPaulo, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));

        assertEquals(invalidOption, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));

        assertEquals(invalidOption, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));

        assertEquals(invalidOption, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));

        assertEquals(invalidOption, fakeConsole.get(i++));
        assertEquals(whereToGo, fakeConsole.get(i++));
    }

    private GameProgress beginProgress() {
        MainCharacter character = new MainCharacter("Uhura", MainCharacter.Gender.FEMALE, 36);

        List<Invasion> invasions = Arrays.asList(
            InvaderArmy.invade(new Location("London", "UK")),
            InvaderArmy.invade(new Location("Tokyo", "Japan")),
            InvaderArmy.invade(new Location("São Paulo", "Brazil"))
        );

        return new GameProgress(character, invasions);
    }

    private GameProgress saveSaoPaulo(GameProgress gameProgress) {
        gameProgress.getInvasions().get(2).setAlienDominatedLocation(false);
        return gameProgress;
    }
}