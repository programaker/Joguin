package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;
import com.gmail.programaker.joguin.util.Messages;

import java.util.Iterator;
import java.util.Properties;
import java.util.function.Consumer;

public class SaveGame {
    private final Properties messages;
    private final GameProgressRepository repository;
    private final GameOver gameOver;

    public SaveGame(
        Properties messages,
        GameProgressRepository repository,
        GameOver gameOver
    ) {
        this.messages = messages;
        this.repository = repository;
        this.gameOver = gameOver;
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
            return SaveGame.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            if (repository.save(gameProgress)) {
                print.accept(Messages.get("success", messages));
            } else {
                print.accept(Messages.get("error", messages));
            }

            return gameOver;
        }
    }
}