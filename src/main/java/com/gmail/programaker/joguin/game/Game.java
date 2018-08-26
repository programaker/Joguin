package com.gmail.programaker.joguin.game;

public class Game {
    private final ShowIntro showIntro;

    public Game(ShowIntro showIntro) {
        this.showIntro = showIntro;
    }

    public GameStep start() {
        return showIntro.start();
    }
}
