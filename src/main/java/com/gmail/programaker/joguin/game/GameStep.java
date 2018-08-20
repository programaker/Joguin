package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public interface GameStep {
    GameStep interactWithPlayer(Consumer<String> askPlayer, Iterator<String> playerAnswers);

    default boolean gameOver() {
        return false;
    }
}
