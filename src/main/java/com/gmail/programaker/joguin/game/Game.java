package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.game.step.GameStep;
import com.gmail.programaker.joguin.game.step.ShowIntro;

/** The starting point of the game
 * It knows what is the first step to be called
 * and then the steps take care of their our
 * sequence
 * */
public class Game {
    private final ShowIntro showIntro;

    public Game(ShowIntro showIntro) {
        this.showIntro = showIntro;
    }

    public GameStep start() {
        return showIntro.start();
    }
}
