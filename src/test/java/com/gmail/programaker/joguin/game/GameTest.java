package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.TestGameComponents;
import com.gmail.programaker.joguin.game.step.GameStep;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest extends BaseTest {
    private Game game;

    public GameTest() {
        game = new TestGameComponents().game();
    }

    @Test
    public void startingGame() {
        GameStep nextStep = game.start();
        assertEquals("Game should start with ShowInfo step", "ShowIntro", nextStep.name());
    }
}