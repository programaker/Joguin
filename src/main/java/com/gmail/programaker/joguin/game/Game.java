package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.game.step.GameStep;
import com.gmail.programaker.joguin.game.step.ShowIntro;

public class Game {
    private final ShowIntro showIntro;

    public Game(ShowIntro showIntro) {
        this.showIntro = showIntro;
    }

    public GameStep start() {
        return showIntro.start();
    }
}
