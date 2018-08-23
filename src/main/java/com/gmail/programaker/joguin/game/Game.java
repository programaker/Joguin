package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Game {
    private final ShowIntro showIntroStep;

    @Autowired
    public Game(ShowIntro showIntroStep) {
        this.showIntroStep = showIntroStep;
    }

    public GameStep start() {
        Locale.setDefault(Locale.US);
        return showIntroStep.start();
    }
}
