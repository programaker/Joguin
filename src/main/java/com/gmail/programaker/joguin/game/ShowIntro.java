package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;

public class ShowIntro implements GameStep {
    private final Messages allMessages;

    public ShowIntro(Messages allMessages) {
        this.allMessages = allMessages;
    }

    @Override
    public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
        ShowIntroMessages messages = allMessages.getShowIntroMessages();
        println.accept(messages.intro());

        String option = AskPlayer.to(messages.start(),
            messages.invalidOption(),
            println,
            playerAnswers,
            String::toLowerCase,
            this::validateOption
        );

        if (option.equals("n")) {
            return new CreateCharacter(allMessages);
        }

        if (option.equals("c")) {
            return new Continue();
        }

        if (option.equals("q")) {
            return new Quit(allMessages.getQuitMessages());
        }

        return new GameOver();
    }

    private boolean validateOption(String option) {
        return option.equals("n") || option.equals("c") || option.equals("q");
    }
}
