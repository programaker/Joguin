package com.gmail.programaker.joguin.game.step;

import java.util.Iterator;
import java.util.function.Consumer;

/** The final step of the game that makes it end */
public class GameOver implements GameStep {
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
        return this;
    }

    @Override
    public boolean gameOver() {
        return true;
    }
}
