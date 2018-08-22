package com.gmail.programaker.joguin.game.createcharacter;

import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.game.AskPlayer;
import com.gmail.programaker.joguin.game.explore.Explore;
import com.gmail.programaker.joguin.game.GameStep;
import com.gmail.programaker.joguin.game.quit.Quit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class CreateCharacter {
    private final CreateCharacterMessages messages;
    private final Explore exploreStep;
    private final Quit quitStep;

    @Autowired
    public CreateCharacter(CreateCharacterMessages messages, Explore exploreStep, Quit quitStep) {
        this.messages = messages;
        this.exploreStep = exploreStep;
        this.quitStep = quitStep;
    }

    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public String name() {
            return CreateCharacter.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            String choice = AskPlayer.to(messages.createOrQuit(),
                messages.invalidChoice(),
                println,
                playerAnswers,
                String::toLowerCase,
                this::validateChoice
            );

            if (choice.equals("q")) {
                return quitStep.start();
            }

            String name = AskPlayer.to(messages.informCharacterName(),
                messages.invalidName(),
                println,
                playerAnswers,
                Function.identity(),
                this::validateName
            );

            MainCharacter.Gender gender = AskPlayer.to(messages.informCharacterGender(),
                messages.invalidGender(),
                println,
                playerAnswers,
                MainCharacter.Gender::byCode,
                this::validateGender
            );

            Integer age = AskPlayer.to(messages.informCharacterAge(),
                messages.invalidAge(),
                println,
                playerAnswers,
                Integer::parseInt,
                this::validateAge
            );

            return exploreStep.start(new MainCharacter(name, gender, age));
        }

        private boolean validateChoice(String choice) {
            if (choice.isEmpty()) {
                return false;
            }

            return choice.equals("c") || choice.equals("q");
        }

        private boolean validateName(String name) {
            return !name.trim().isEmpty();
        }

        private boolean validateGender(MainCharacter.Gender gender) {
            return !gender.equals(MainCharacter.Gender.NONE);
        }

        private boolean validateAge(Integer age) {
            return age > 18;
        }
    }
}
