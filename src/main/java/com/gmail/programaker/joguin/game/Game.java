package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {
    private final ShowIntro showIntroStep;

    @Autowired
    public Game(ShowIntro showIntroStep) {
        this.showIntroStep = showIntroStep;
    }

    public GameStep start() {
        return showIntroStep.start();
    }
}
