package com.gmail.programaker.joguin.game.step;

import java.util.Iterator;
import java.util.function.Consumer;

/** Represents a game step with its corresponding interactions with the player
 * It may or may not ends the whole game */
public interface GameStep {
    GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers);

    String name();

    default boolean gameOver() {
        return false;
    }
}
