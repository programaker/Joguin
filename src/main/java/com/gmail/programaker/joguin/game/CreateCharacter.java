package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.character.Gender;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

public final class CreateCharacter implements GameStep {
    private final Messages allMessages;

    public CreateCharacter(Messages messages) {
        this.allMessages = messages;
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

        String name = AskPlayer.to(messages.informName(),
            messages.invalidName(),
            println,
            playerAnswers,
            Function.identity(),
            this::validateName
        );

        Gender gender = AskPlayer.to(messages.informGender(),
            messages.invalidGender(),
            println,
            playerAnswers,
            Gender::byCode,
            this::validateGender
        );

        Integer age = AskPlayer.to(messages.informAge(),
            messages.invalidAge(),
            println,
            playerAnswers,
            Integer::parseInt,
            this::validateAge
        );

        return new Explore();
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
