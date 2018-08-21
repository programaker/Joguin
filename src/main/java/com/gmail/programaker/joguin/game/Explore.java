package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public final class Explore implements GameStep {
    @Override
    public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
        return new GameOver();
    }
}
