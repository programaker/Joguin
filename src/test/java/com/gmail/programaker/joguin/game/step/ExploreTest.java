package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.game.component.TestGameComponents;
import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.util.TestUtil.beginProgress;
import static com.gmail.programaker.joguin.util.TestUtil.blackHoleConsole;
import static org.junit.Assert.assertEquals;

public class ExploreTest extends BaseTest {
    private final String london = "1. \uD83D\uDC7D London - UK\n";
    private final String savedLondon = "1. \uD83C\uDF0D London - UK\n";

    private final String tokyo = "2. \uD83D\uDC7D Tokyo - Japan\n";
    private final String savedTokyo = "2. \uD83C\uDF0D Tokyo - Japan\n";

    private final String saoPaulo = "3. \uD83D\uDC7D São Paulo - Brazil\n";
    private final String savedSaoPaulo = "3. \uD83C\uDF0D São Paulo - Brazil\n";

    private final String whereToGo = "\nWhere do you want to go? - (1) to (3), (Q)uit:\n";
    private final String invalidOption = "Invalid option\n";

    private final String missionAccomplished = "\nAll Terraform Devices are destroyed and the Earth is saved!\n" +
        "However, the Zorblaxians are still out there, expanding their galactic empire.\n" +
        "They may return one day, but they must think twice before mess with the Earth again!\n" +
        "\n" +
        "THE END.\n";

    private Explore explore;

    public ExploreTest() {
        explore = new TestGameComponents().explore();
    }

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        explore.start(beginProgress()).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("1").iterator()
        );

        int i = 0;
        assertEquals("\n", fakeConsole.get(i++));
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
        assertEquals("\n", fakeConsole.get(i++));
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

        assertEquals("\n", fakeConsole.get(i++));
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

    @Test
    public void whenAllCitiesAreSaved() {
        List<String> fakeConsole = new ArrayList<>();

        GameProgress progress = beginProgress();
        for (int n = 1; n <= progress.getInvasions().size(); n++) {
            progress.defeatInvasion(n);
        }

        GameStep nextStep = explore.start(progress).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        int i = 0;
        assertEquals("\n", fakeConsole.get(i++));
        assertEquals(savedLondon, fakeConsole.get(i++));
        assertEquals(savedTokyo, fakeConsole.get(i++));
        assertEquals(savedSaoPaulo, fakeConsole.get(i++));
        assertEquals("Should have printed mission accomplished message", missionAccomplished, fakeConsole.get(i++));

        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    private GameProgress saveSaoPaulo(GameProgress gameProgress) {
        return gameProgress.defeatInvasion(3);
    }
}