package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.TestGameConfig;
import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.util.BaseTest;
import com.gmail.programaker.joguin.zorblax.Invasion;
import com.gmail.programaker.joguin.zorblax.TerraformDevice;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class FightTest extends BaseTest {
    private final String report = "\nCommander Barbarella, we are getting closer to the Terraform Device of Rio de Janeiro.\n" +
        "It is huge! Soon its automatic defense system will detect us and start to attack!\n" +
        "According to our analysis, the defense power of this Device is 100.\n";

    private final String giveOrder = "\nWhat are your orders? - (F)ight, (R)etreat:\n";

    private final String animationEarth = "\uD83C\uDF0D";
    private final String animationEarthWeapon = "\uD83D\uDE80";

    private final String animationAlien = "\uD83D\uDC7D";
    private final String animationAlienWeapon = "\u26A1\uFE0F";

    private final String animationStrike = "\uD83D\uDCA5";

    private final String errorInvalidOption = "Invalid option\n";

    private final String earthWon = "\nCongratulations! Thanks to your command, our army has destroyed the Terraform Device!\n" +
        "Go ahead and save more cities!\n" +
        "You have now {0} experience points.\n";

    private final String aliensWon = "\nOur army was defeated! This Terraform Device is too powerful!\n" +
        "Retreat for now and try to save Rio de Janeiro another time.\n" +
        "You have now {0} experience points.\n";

    private final String locationAlreadySaved = "\nGood to see Rio de Janeiro being rebuilt after the destruction of the Terraform Device!\n" +
        "Life is slowly getting back to normal!\n";

    private Fight fight;

    public FightTest() {
        fight = new TestGameConfig().fight();
    }

    @Test
    public void playerFightsAndLoses() {
        List<String> fakeConsole = new ArrayList<>();
        GameProgress progress = beginProgress();

        GameStep nextStep = interactionsWithPlayerUntilAfterFight(progress, fakeConsole);

        String aliensWonF = MessageFormat.format(aliensWon, 50);
        assertEquals("Should have printed alien victory", aliensWonF, fakeConsole.get(fakeConsole.size() - 1));
        assertEquals("Character should have gained half Device's power of experience", 50, progress.getCharacterExperience());
        assertTrue("Location should be still dominated by the aliens", progress.getInvasion(1).isAlienDominatedLocation());
        assertEquals("Should have going back to Explore", "Explore", nextStep.name());
    }

    @Test
    public void playerFightsAndWins() {
        List<String> fakeConsole = new ArrayList<>();
        GameProgress progress = beginProgress().increaseCharacterExperience(100);

        GameStep nextStep = interactionsWithPlayerUntilAfterFight(progress, fakeConsole);

        String earthWonF = MessageFormat.format(earthWon, 150);
        assertEquals("Should have printed Earth's victory", earthWonF, fakeConsole.get(fakeConsole.size() - 1));
        assertEquals("Character should have gained half Device's power of experience", 150, progress.getCharacterExperience());
        assertFalse("Location should be free from the aliens", progress.getInvasion(1).isAlienDominatedLocation());
        assertEquals("Should have going back to Explore", "Explore", nextStep.name());
    }

    @Test
    public void playerEntersAnInvasionAlreadyDefeated() {
        List<String> fakeConsole = new ArrayList<>();

        GameProgress progress = beginProgress()
            .defeatInvasion(1)
            .increaseCharacterExperience(200);

        GameStep nextStep = fight.start(1, progress).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertEquals("Should have printed location saved message", locationAlreadySaved, fakeConsole.get(0));
        assertEquals("Character experience should be the same", 200, progress.getCharacterExperience());
        assertFalse("Location should still be free from the aliens", progress.getInvasion(1).isAlienDominatedLocation());
        assertEquals("Should have going back to Explore", "Explore", nextStep.name());
    }

    @Test
    public void playerRetreatFromFight() {
        List<String> fakeConsole = new ArrayList<>();
        GameProgress progress = beginProgress();

        GameStep nextStep = fight.start(1, progress).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("R").iterator()
        );

        int i = 0;
        assertEquals("Printed more messages than expected", 2, fakeConsole.size());
        assertEquals("Should have printed the report", report, fakeConsole.get(i++));
        assertEquals("Should have asked to give an order", giveOrder, fakeConsole.get(i++));
        assertEquals("Should have going back to Explore", "Explore", nextStep.name());
    }

    @Test
    public void givenInvalidOption() {
        List<String> fakeConsole = new ArrayList<>();
        GameProgress progress = beginProgress();

        GameStep nextStep = fight.start(1, progress).interactWithPlayer(
            fakeConsole::add,
            Arrays.asList("1", "meh", "", "r").iterator()
        );

        int i = 0;
        assertEquals("Should have printed the report", report, fakeConsole.get(i++));

        assertEquals(giveOrder, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(giveOrder, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(giveOrder, fakeConsole.get(i++));
        assertEquals(errorInvalidOption, fakeConsole.get(i++));

        assertEquals(giveOrder, fakeConsole.get(i++));
    }

    private GameStep interactionsWithPlayerUntilAfterFight(GameProgress progress, List<String> fakeConsole) {
        GameStep nextStep = fight.start(1, progress).interactWithPlayer(
            fakeConsole::add,
            Collections.singletonList("F").iterator()
        );

        int i = 0;
        int attacks = 31;

        assertEquals("Should have printed the report", report, fakeConsole.get(i++));
        assertEquals("Should have asked to give an order", giveOrder, fakeConsole.get(i++));

        assertEquals("\n", fakeConsole.get(i++));
        assertEquals("Should have printed Earth", animationEarth, fakeConsole.get(i++));
        for (int n = 1; n <= attacks; n++) {
            if (n%2 == 0) {
                assertEquals(n + " should be Earth's weapon", animationEarthWeapon, fakeConsole.get(i++));
            } else {
                assertEquals(n + " should be a blank space", " ", fakeConsole.get(i++));
            }
        }
        assertEquals("Should have printed the strike", animationStrike, fakeConsole.get(i++));
        assertEquals("\n", fakeConsole.get(i++));

        assertEquals("\n", fakeConsole.get(i++));
        assertEquals("Should have printed alien", animationAlien, fakeConsole.get(i++));
        for (int n = 1; n <= attacks; n++) {
            if (n%2 == 0) {
                assertEquals(n + " should be Alien's weapon", animationAlienWeapon, fakeConsole.get(i++));
            } else {
                assertEquals(n + " should be a blank space", " ", fakeConsole.get(i++));
            }
        }
        assertEquals("Should have printed the strike", animationStrike, fakeConsole.get(i++));
        assertEquals("\n", fakeConsole.get(i++));

        return nextStep;
    }

    private GameProgress beginProgress() {
        MainCharacter character = new MainCharacter("Barbarella", MainCharacter.Gender.FEMALE, 30);

        List<Invasion> invasions = Collections.singletonList(
            new Invasion(new TerraformDevice(100), new Location("Rio de Janeiro", "Brazil"))
        );

        return new GameProgress(character, invasions);
    }
}