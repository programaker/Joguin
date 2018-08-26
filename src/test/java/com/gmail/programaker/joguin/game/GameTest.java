package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class GameTest extends BaseTest {
    @Autowired
    private Game game;

    @Test
    public void startingGame() {
        GameStep nextStep = game.start();
        assertEquals("Game should start with ShowInfo step", "ShowIntro", nextStep.name());
    }
}