package com.gmail.programaker.joguin.game.explore;

import com.gmail.programaker.joguin.game.GameStep;
import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.game.quit.Quit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class Explore {
    private final Quit quitStep;

    @Autowired
    public Explore(Quit quitStep) {
        this.quitStep = quitStep;
    }

    public GameStep start(GameProgress gameProgress) {
        return this.new Step(gameProgress);
    }

    private class Step implements GameStep {
        private final GameProgress gameProgress;

        private Step(GameProgress gameProgress) {
            this.gameProgress = gameProgress;
        }

        @Override
        public String name() {
            return Explore.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            return quitStep.start();
        }
    }
}
