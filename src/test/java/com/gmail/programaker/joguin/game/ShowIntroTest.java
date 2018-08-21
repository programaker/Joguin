package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.TestConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.TestConfig.blackHoleConsole;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ShowIntroTest {
    private final String intro =
        "The Zorblaxians arrived on Earth planning to conquer it to expand their galactic empire.\n" +
        "\n" +
        "To do so, they installed several Terraform Devices around the planet, to make our environment \n" +
        "suitable for their biology, extinguishing all earthlings in the process.\n" +
        "\n" +
        "Your job is to lead humanity's army in a mission to destroy all Terraform Devices. \n" +
        "We managed to discover their locations and they will be sent to you, but beware! The Devices \n" +
        "are well guarded by Zorblaxian warriors!";

    private final String start = "(N)ew Game, (C)ontinue, (Q)uit:";
    private final String errorInvalidOption = "Invalid option";

    private ShowIntro showIntro;

    @Autowired
    private Messages messages;

    @Before
    public void setup() {
        showIntro = new ShowIntro(messages);
    }

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro.interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("Q").iterator()
        );

        int i = 0;
        assertEquals("Should have displayed the game intro", intro, fakeConsole.get(i++));
        assertEquals("Should asked a start option", start, fakeConsole.get(i++));
    }

    @Test
    public void whenThePlayerAsksToStartNewGame() {
        GameStep nextStep = showIntro.interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("N").iterator()
        );

        assertTrue("Should have gone to Create Character step", nextStep instanceof CreateCharacter);
    }

    @Test
    public void whenThePlayerAsksToQuit() {
        GameStep nextStep = showIntro.interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertTrue("Should have gone to Quit step", nextStep instanceof Quit);
    }

    @Test
    public void whenThePlayerAsksToContinueGame() {
        GameStep nextStep = showIntro.interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("C").iterator()
        );

        assertTrue("Should have gone to Continue step", nextStep instanceof Continue);
    }

    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro.interactWithPlayer(
            fakeConsole::add,
            Arrays.asList(" ", "meh", "N").iterator()
        );

        int i = 0;

        assertEquals(start, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(start, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(start, fakeConsole.get(i++));
    }
}