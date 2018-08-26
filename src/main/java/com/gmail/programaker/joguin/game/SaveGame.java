package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class SaveGame {
    private final MessageSource messages;
    private final GameProgressRepository repository;
    private final GameOver gameOver;

    @Autowired
    public SaveGame(
        @Qualifier("SaveGameMessages") MessageSource messages,
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