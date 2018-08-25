package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.config.TestConfig.blackHoleConsole;
import static org.junit.Assert.assertEquals;

public class ShowIntroTest extends BaseTest {
    private final String intro =
        "\nThe Zorblaxians arrived on Earth planning to conquer it to expand their galactic empire.\n" +
        "\n" +
        "To do so, they installed several Terraform Devices around the planet, to make our environment\n" +
        "suitable for their biology, extinguishing all earthlings in the process.\n" +
        "\n" +
        "Your job is to lead humanity's army in a mission to destroy all Terraform Devices.\n" +
        "We managed to discover their locations and they will be sent to you, but beware! The Devices\n" +
        "have strong defenses!\n";

    private final String start = "\n(N)ew Game, (C)ontinue, (Q)uit:\n";
    private final String errorInvalidOption = "Invalid option\n";

    @Autowired
    private ShowIntro showIntro;

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro.start().interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("Q").iterator()
        );

        int i = 0;
        assertEquals("Should have displayed the game intro", intro, fakeConsole.get(i++));
        assertEquals("Should asked a start option", start, fakeConsole.get(i++));
    }

    @Test
    public void whenThePlayerAsksToStartNewGame() {
        GameStep nextStep = showIntro.start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("N").iterator()
        );

        assertEquals("Should have gone to Create Character step", "CreateCharacter", nextStep.name());
    }

    @Test
    public void whenThePlayerAsksToQuit() {
        GameStep nextStep = showIntro.start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertEquals("Should have gone to Quit step", "Quit", nextStep.name());
    }

    @Test
    public void whenThePlayerAsksToContinueGame() {
        GameStep nextStep = showIntro.start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("C").iterator()
        );

        assertEquals("Should have gone to Continue step", "Continue", nextStep.name());
    }

    @Test
    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro.start().interactWithPlayer(
            fakeConsole::add,
            Arrays.asList(" ", "meh", "N").iterator()
        );

        int i = 0;
        assertEquals(intro, fakeConsole.get(i++));

        assertEquals(start, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(start, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(start, fakeConsole.get(i++));
    }
}