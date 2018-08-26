package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.TestConfig;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static com.gmail.programaker.joguin.config.TestConfig.beginProgress;
import static org.junit.Assert.*;

public class SaveGameTest extends BaseTest {
    private final String success = "\nGame saved.\n";
    private final String error = "\nError saving the game. Damn Zorblaxians!\n";

    private Properties messages;

    private GameOver gameOver;

    @Test
    public void gameSavedSuccessfuly() {
        TestConfig.MockGameProgressRepository repo = new TestConfig.MockGameProgressRepository(false);
        assertFalse("Repository should start empty", repo.savedProgressExists());

        List<String> fakeConsole = new ArrayList<>();

        GameStep nextStep = saveGame(repo).start(beginProgress().defeatInvasion(1)).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertEquals("Should have printed success message", success, fakeConsole.get(0));
        assertTrue("Repository should end filled", repo.savedProgressExists());
        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    @Test
    public void errorSavingGame() {
        TestConfig.MockGameProgressRepository repo = new TestConfig.MockGameProgressRepository(true);
        assertFalse("Repository should start empty", repo.savedProgressExists());

        List<String> fakeConsole = new ArrayList<>();

        GameStep nextStep = saveGame(repo).start(beginProgress().defeatInvasion(1)).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertEquals("Should have printed error message", error, fakeConsole.get(0));
        assertFalse("Repository should end empty", repo.savedProgressExists());
        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    private SaveGame saveGame(GameProgressRepository repository) {
        return new SaveGame(messages, repository, gameOver);
    }
}