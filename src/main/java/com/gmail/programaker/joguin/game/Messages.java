package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Messages {
    private final ShowIntroMessages showIntroMessages;
    private final CreateCharacterMessages createCharacterMessages;
    private final QuitMessages quitMessages;

    @Autowired
    public Messages(
        ShowIntroMessages showIntroMessages,
        CreateCharacterMessages createCharacterMessages,
        QuitMessages quitMessages
    ) {
        this.showIntroMessages = showIntroMessages;
        this.createCharacterMessages = createCharacterMessages;
        this.quitMessages = quitMessages;
    }

    public ShowIntroMessages getShowIntroMessages() {
        return showIntroMessages;
    }

    public CreateCharacterMessages getCreateCharacterMessages() {
        return createCharacterMessages;
    }

    public QuitMessages getQuitMessages() {
        return quitMessages;
    }
}
