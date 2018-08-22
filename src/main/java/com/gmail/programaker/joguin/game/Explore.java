package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public class Explore implements GameStep {
    private final AllMessages allMessages;

    public Explore(AllMessages allMessages) {
        this.allMessages = allMessages;
    }

    @Override
    public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
        return new Quit(allMessages.getQuitMessages());
    }
}
