package com.gmail.programaker.joguin.game.step;

import java.util.Iterator;
import java.util.function.Consumer;

public interface GameStep {
    GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers);

    String name();

    default boolean gameOver() {
        return false;
    }
}
