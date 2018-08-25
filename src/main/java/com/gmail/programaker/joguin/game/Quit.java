package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class Quit {
    private final MessageSource messages;

    @Autowired
    public Quit(
        @Qualifier("QuitMessages") MessageSource messages
    ) {
        this.messages = messages;
    }

    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public String name() {
            return Quit.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            String saveGame = AskPlayer.to(Messages.get("want-to-save-game", messages),
                Messages.get("error-invalid-option", messages),
                println,
                playerAnswers,
                String::toLowerCase,
                this::validateSaveGame
            );

            if (saveGame.equals("y")) {
                //Save the game
            }

            return new GameOver();
        }

        private boolean validateSaveGame(String saveGame) {
            return saveGame.equals("y") || saveGame.equals("n");
        }
    }
}
