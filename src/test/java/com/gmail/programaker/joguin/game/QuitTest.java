package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.config.TestConfig.beginProgress;
import static com.gmail.programaker.joguin.config.TestConfig.blackHoleConsole;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuitTest extends BaseTest {
    private final String wantToSaveGame = "\nSave the game? - (Y)es, (N)o:\n";
    private final String errorInvalidOption = "Invalid option\n";

    private Quit quit;

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        quit.start(beginProgress()).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("N").iterator()
        );

        assertEquals("Should have asked if the Player wanted to save the game", wantToSaveGame, fakeConsole.get(0));
    }

    @Test
    public void playerDoesNotWantToSaveTheGame() {
        GameStep nextStep = quit.start(beginProgress()).interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("N").iterator()
        );

        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    @Test
    public void playerWantsToSaveTheGame() {
        GameStep nextStep = quit.start(beginProgress()).interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Y").iterator()
        );

        assertEquals("Should have gone to SaveGame step", "SaveGame", nextStep.name());
    }

    @Test
    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();

        quit.start(beginProgress()).interactWithPlayer(
            fakeConsole::add,
            Arrays.asList("A", "9", "Y").iterator()
        );

        //Should print error message and ask again until receive a correct answer

        int i = 0;

        assertEquals(wantToSaveGame, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(wantToSaveGame, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(wantToSaveGame, fakeConsole.get(i++));
    }

    @Test
    public void quitWithoutGameProgressToSave() {
        List<String> fakeConsole = new ArrayList<>();

        GameStep nextStep = quit.start().interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertTrue("No messages to the user should have been printed", fakeConsole.isEmpty());
        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }
}
