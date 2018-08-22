package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.character.Gender;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class CreateCharacter implements GameStep {
    private final AllMessages allMessages;

    public CreateCharacter(AllMessages allMessages) {
        this.allMessages = allMessages;
    }

    @Override
    public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
        CreateCharacterMessages messages = allMessages.getCreateCharacterMessages();

        String choice = AskPlayer.to(messages.createOrQuit(),
            messages.invalidChoice(),
            println,
            playerAnswers,
            String::toLowerCase,
            this::validateChoice
        );

        if (choice.equals("q")) {
            return new Quit(allMessages.getQuitMessages());
        }

        String name = AskPlayer.to(messages.informCharacterName(),
            messages.invalidName(),
            println,
            playerAnswers,
            Function.identity(),
            this::validateName
        );

        Gender gender = AskPlayer.to(messages.informCharacterGender(),
            messages.invalidGender(),
            println,
            playerAnswers,
            Gender::byCode,
            this::validateGender
        );

        Integer age = AskPlayer.to(messages.informCharacterAge(),
            messages.invalidAge(),
            println,
            playerAnswers,
            Integer::parseInt,
            this::validateAge
        );

        return new Explore(allMessages);
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

    private boolean validateGender(Gender gender) {
        return !gender.equals(Gender.NONE);
    }

    private boolean validateAge(Integer age) {
        return age > 18;
    }
}
