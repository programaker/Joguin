package com.gmail.programaker.joguin.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Game {
    private final ShowIntro showIntro;

    @Autowired
    public Game(ShowIntro showIntro) {
        this.showIntro = showIntro;
    }

    public GameStep start() {
        Locale.setDefault(Locale.US);
        return showIntro.start();
    }
}
