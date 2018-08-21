package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public class Quit implements GameStep {
    private final QuitMessages messages;

    public Quit(QuitMessages messages) {
        this.messages = messages;
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
