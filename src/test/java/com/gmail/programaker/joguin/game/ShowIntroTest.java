package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.*;

import static com.gmail.programaker.joguin.config.TestConfig.*;
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

    private final String start = "\n(N)ew Game, (Q)uit:\n";
    private final String startWithResume = "\n(N)ew Game, (R)esume, (Q)uit:\n";
    private final String errorInvalidOption = "Invalid option\n";
    private final String welcomeBack = "\nWelcome back, commander Uhura! You have 500 points of experience.\n";

    private Properties messages;

    private CreateCharacter createCharacter;

    private Explore explore;

    private Quit quit;

    @Test
    public void interactionsWithPlayerWithoutGameToResume() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro(emptyGameProgressRepository).start().interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("Q").iterator()
        );

        int i = 0;
        assertEquals("Should have displayed the game intro", intro, fakeConsole.get(i++));
        assertEquals("Should asked a start option", start, fakeConsole.get(i++));
    }

    @Test
    public void interactionsWithPlayerWithGameToResume() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro(fullGameProgressRepository).start().interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("Q").iterator()
        );

        int i = 0;
        assertEquals("Should have displayed the game intro", intro, fakeConsole.get(i++));
        assertEquals("Should asked a start option", startWithResume, fakeConsole.get(i++));
    }

    @Test
    public void whenThePlayerAsksToStartNewGame() {
        GameStep nextStep = showIntro(emptyGameProgressRepository).start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("N").iterator()
        );

        assertEquals("Should have gone to Create Character step", "CreateCharacter", nextStep.name());
    }

    @Test
    public void whenThePlayerAsksToQuit() {
        GameStep nextStep = showIntro(emptyGameProgressRepository).start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertEquals("Should have gone to Quit step", "Quit", nextStep.name());
    }

    @Test
    public void whenThePlayerAsksToResumeGame() {
        List<String> fakeConsole = new ArrayList<>();

        MockGameProgressRepository repo = new MockGameProgressRepository(false);
        repo.save(beginProgress().increaseCharacterExperience(500));

        GameStep nextStep = showIntro(repo).start().interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("R").iterator()
        );

        int i = 0;
        assertEquals("Should have displayed the game intro", intro, fakeConsole.get(i++));
        assertEquals("Should asked a start option", startWithResume, fakeConsole.get(i++));
        assertEquals("Should have print welcome back message", welcomeBack, fakeConsole.get(i++));
        assertEquals("Should have gone to Explore step", "Explore", nextStep.name());
    }

    @Test
    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();

        showIntro(emptyGameProgressRepository).start().interactWithPlayer(
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

    private ShowIntro showIntro(GameProgressRepository repository) {
        return new ShowIntro(messages, repository, createCharacter, explore, quit);
    }
}