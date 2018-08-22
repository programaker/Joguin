package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {
    private final AllMessages allMessages;

    @Autowired
    public Game(AllMessages allMessages) {
        this.allMessages = allMessages;
    }

    public GameStep start() {
        return new ShowIntro(allMessages);
    }
}
