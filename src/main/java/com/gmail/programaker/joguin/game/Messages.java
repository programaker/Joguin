package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class Messages {
    private final CreateCharacterMessages createCharacterMessages;
    private final QuitMessages quitMessages;

    @Autowired
    public Messages(
        CreateCharacterMessages createCharacterMessages,
        QuitMessages quitMessages
    ) {
        this.createCharacterMessages = createCharacterMessages;
        this.quitMessages = quitMessages;
    }

    public CreateCharacterMessages getCreateCharacterMessages() {
        return createCharacterMessages;
    }

    public QuitMessages getQuitMessages() {
        return quitMessages;
    }
}
