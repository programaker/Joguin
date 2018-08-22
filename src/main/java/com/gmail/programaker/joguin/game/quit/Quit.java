package com.gmail.programaker.joguin.game.quit;

import com.gmail.programaker.joguin.game.AskPlayer;
import com.gmail.programaker.joguin.game.GameOver;
import com.gmail.programaker.joguin.game.GameStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class Quit {
    private final QuitMessages messages;

    @Autowired
    public Quit(QuitMessages messages) {
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
            String saveGame = AskPlayer.to(messages.informIfWantToSaveGame(),
                messages.invalidOption(),
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
