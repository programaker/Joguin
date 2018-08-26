package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;

import java.util.Iterator;
import java.util.Properties;
import java.util.function.Consumer;

public class Quit {
    private final Properties messages;
    private final SaveGame saveGame;
    private final GameOver gameOver;

    public Quit(
        Properties messages,
        SaveGame saveGame,
        GameOver gameOver
    ) {
        this.messages = messages;
        this.saveGame = saveGame;
        this.gameOver = gameOver;
    }

    public GameStep start(GameProgress gameProgress) {
        return this.new Step(gameProgress);
    }

    public GameStep start() {
        return start(null);
    }

    private class Step implements GameStep {
        private final GameProgress gameProgress;

        private Step(GameProgress gameProgress) {
            this.gameProgress = gameProgress;
        }

        @Override
        public String name() {
            return Quit.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            if (gameProgress != null) {
                String option = AskPlayer.to(Messages.get("want-to-save-game", messages),
                    Messages.get("error-invalid-option", messages),
                    print,
                    playerAnswers,
                    String::toLowerCase,
                    this::validateSaveGame
                );

                if (option.equals("y")) {
                    return saveGame.start(gameProgress);
                }
            }

            return gameOver;
        }

        private boolean validateSaveGame(String saveGame) {
            return saveGame.equals("y") || saveGame.equals("n");
        }
    }
}
