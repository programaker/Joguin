package com.gmail.programaker.joguin.game.component;

import com.gmail.programaker.joguin.util.StandardRepositoryFactory;

public class StandardGameComponents extends GameComponents {
    public StandardGameComponents() {
        super(
            new StandardRepositoryFactory(),

            millis -> {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    // ¯\_(ツ)_/¯
                }
            }
        );
    }
}
