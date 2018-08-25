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
public class ShowIntro {
    private final MessageSource messages;
    private final CreateCharacter createCharacterStep;
    private final Continue continueStep;
    private final Quit quitStep;

    @Autowired
    public ShowIntro(
        @Qualifier("ShowIntroMessages") MessageSource messages,
        CreateCharacter createCharacterStep,
        Continue continueStep,
        Quit quitStep
    ) {
        this.messages = messages;
        this.createCharacterStep = createCharacterStep;
        this.continueStep = continueStep;
        this.quitStep = quitStep;
    }

    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public String name() {
            return ShowIntro.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            println.accept(Messages.get("intro", messages));

            String option = AskPlayer.to(Messages.get("start", messages),
                Messages.get("error-invalid-option", messages),
                println,
                playerAnswers,
                String::toLowerCase,
                this::validateOption
            );

            if (option.equals("n")) {
                return createCharacterStep.start();
            }

            if (option.equals("c")) {
                return continueStep.start();
            }

            if (option.equals("q")) {
                return quitStep.start();
            }

            return new GameOver();
        }

        private boolean validateOption(String option) {
            return option.equals("n") || option.equals("c") || option.equals("q");
        }
    }
}
