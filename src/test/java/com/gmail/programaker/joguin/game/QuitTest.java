package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.MessageConfig;
import com.gmail.programaker.joguin.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.config.TestConfig.blackHoleConsole;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, MessageConfig.class})
public class QuitTest {
    private final String wantToSaveGame = "\nSave the game? (Y/N):";
    private final String errorInvalidOption = "Invalid option";

    @Autowired
    private Quit quit;

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        quit.start().interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("N").iterator()
        );

        assertEquals("Should have asked if the Player wanted to save the game", wantToSaveGame, fakeConsole.get(0));
    }

    @Test
    public void givenValidOption() {
        GameStep nextStep = quit.start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("N").iterator()
        );

        assertTrue("Should have gone to GameOver step", nextStep instanceof GameOver);
    }

    @Test
    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();

        quit.start().interactWithPlayer(
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
}
