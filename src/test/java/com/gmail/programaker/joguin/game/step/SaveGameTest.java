package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.game.component.GameComponents;
import com.gmail.programaker.joguin.game.component.TestGameComponents;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;
import com.gmail.programaker.joguin.util.BaseTest;
import com.gmail.programaker.joguin.util.TestRepositoryFactory;
import com.gmail.programaker.joguin.util.TestUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gmail.programaker.joguin.util.TestUtil.beginProgress;
import static org.junit.Assert.*;

public class SaveGameTest extends BaseTest {
    private final String success = "\nGame saved.\n";
    private final String error = "\nError saving the game. Damn Zorblaxians!\n";

    private GameProgressRepository saveGameRepository;
    private GameProgressRepository errorSaveGameRepository;

    private SaveGame saveGame;
    private SaveGame errorSaveGame;

    public SaveGameTest() {
        GameComponents testGameComponents = new TestGameComponents();
        GameComponents saveGameErrorGameComponents = new SaveGameErrorGameComponents();

        saveGame = testGameComponents.saveGame();
        saveGameRepository = testGameComponents.repositoryConfig().gameProgressRepository();

        errorSaveGame = saveGameErrorGameComponents.saveGame();
        errorSaveGameRepository = saveGameErrorGameComponents.repositoryConfig().gameProgressRepository();
    }

    @Test
    public void gameSavedSuccessfuly() {
        assertFalse("Repository should start empty", saveGameRepository.savedProgressExists());

        List<String> fakeConsole = new ArrayList<>();

        GameStep nextStep = saveGame.start(beginProgress().defeatInvasion(1)).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertEquals("Should have printed success message", success, fakeConsole.get(0));
        assertTrue("Repository should end filled", saveGameRepository.savedProgressExists());
        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    @Test
    public void errorSavingGame() {
        assertFalse("Repository should start empty", errorSaveGameRepository.savedProgressExists());

        List<String> fakeConsole = new ArrayList<>();

        GameStep nextStep = errorSaveGame.start(beginProgress().defeatInvasion(1)).interactWithPlayer(
            fakeConsole::add,
            Collections.<String>emptyList().iterator()
        );

        assertEquals("Should have printed error message", error, fakeConsole.get(0));
        assertFalse("Repository should end empty", errorSaveGameRepository.savedProgressExists());
        assertEquals("Should have gone to GameOver step", "GameOver", nextStep.name());
    }

    private static final class SaveGameErrorGameComponents extends GameComponents {
        private SaveGameErrorGameComponents() {
            super(new TestRepositoryFactory(true), TestUtil.sleep);
        }
    }
}