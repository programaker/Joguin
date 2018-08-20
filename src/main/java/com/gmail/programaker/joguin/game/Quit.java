package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public final class Quit implements GameStep {
    @Override
    public GameStep interactWithPlayer(Consumer<String> askPlayer, Iterator<String> playerAnswers) {
        return null;
    }
}
