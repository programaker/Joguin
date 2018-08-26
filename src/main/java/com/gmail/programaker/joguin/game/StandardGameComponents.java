package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.GameComponents;
import com.gmail.programaker.joguin.config.StandardRepositoryConfig;

public class StandardGameComponents extends GameComponents {
    public StandardGameComponents() {
        super(
            new StandardRepositoryConfig(),

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
