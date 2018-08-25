package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

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
