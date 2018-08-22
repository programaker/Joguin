package com.gmail.programaker.joguin.game;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class Continue {
    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            return new GameOver();
        }

        @Override
        public String name() {
            return Continue.class.getSimpleName();
        }
    }
}
