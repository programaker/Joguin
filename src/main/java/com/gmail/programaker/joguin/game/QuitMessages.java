package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:QuitMessages.properties")
public class QuitMessages {
    private final String askWantToSaveGame;
    private final String invalidOption;

    @Autowired
    public QuitMessages(
        @Value("${ask-want-to-save-game}")
        String askWantToSaveGame,

        @Value("${error-invalid-option}")
        String invalidOption
    ) {
        this.askWantToSaveGame = askWantToSaveGame;
        this.invalidOption = invalidOption;
    }

    public String informIfWantToSaveGame() {
        return askWantToSaveGame;
    }

    public String invalidOption() {
        return invalidOption;
    }
}
