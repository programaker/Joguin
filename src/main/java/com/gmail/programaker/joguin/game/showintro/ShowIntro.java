package com.gmail.programaker.joguin.game.showintro;

import com.gmail.programaker.joguin.game.AskPlayer;
import com.gmail.programaker.joguin.game.Continue;
import com.gmail.programaker.joguin.game.GameOver;
import com.gmail.programaker.joguin.game.GameStep;
import com.gmail.programaker.joguin.game.createcharacter.CreateCharacter;
import com.gmail.programaker.joguin.game.quit.Quit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class ShowIntro {
    private final ShowIntroMessages messages;
    private final CreateCharacter createCharacterStep;
    private final Continue continueStep;
    private final Quit quitStep;

    @Autowired
    public ShowIntro(
        ShowIntroMessages messages,
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
            println.accept(messages.intro());

            String option = AskPlayer.to(messages.start(),
                messages.invalidOption(),
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
