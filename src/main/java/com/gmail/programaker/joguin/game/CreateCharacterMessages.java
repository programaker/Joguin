package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:CreateCharacterMessages.properties")
public final class CreateCharacterMessages {
    private final String askToCreateOrQuit;
    private final String askName;
    private final String askGender;
    private final String askAge;

    private final String invalidChoice;
    private final String invalidName;
    private final String invalidGender;
    private final String invalidAge;

    @Autowired
    public CreateCharacterMessages(
        @Value("${ask-to-create-or-quit}")
        String askToCreateOrQuit,

        @Value("${ask-name}")
        String askName,

        @Value("${ask-gender}")
        String askGender,

        @Value("${ask-age}")
        String askAge,

        @Value("${error-invalid-choice}")
        String invalidChoice,

        @Value("${error-invalid-name}")
        String invalidName,

        @Value("${error-invalid-gender}")
        String invalidGender,

        @Value("${error-invalid-age}")
        String invalidAge
    ) {
        this.askToCreateOrQuit = askToCreateOrQuit;
        this.askName = askName;
        this.askGender = askGender;
        this.askAge = askAge;
        this.invalidChoice = invalidChoice;
        this.invalidName = invalidName;
        this.invalidGender = invalidGender;
        this.invalidAge = invalidAge;
    }

    public String createOrQuit() {
        return askToCreateOrQuit;
    }

    public String informName() {
        return askName;
    }

    public String informGender() {
        return askGender;
    }

    public String informAge() {
        return askAge;
    }

    public String invalidChoice() {
        return invalidChoice;
    }

    public String invalidName() {
        return invalidName;
    }

    public String invalidGender() {
        return invalidGender;
    }

    public String invalidAge() {
        return invalidAge;
    }
}
