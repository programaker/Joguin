package com.gmail.programaker.joguin.game.component;

import com.gmail.programaker.joguin.util.StandardRepositoryFactory;

/** GameComponents subclass that inject the standard dependencies in it */
public class StandardGameComponents extends GameComponents {
    public StandardGameComponents() {
        super(
            new StandardRepositoryFactory(),

            //Sleeping to make fight "animations" more exciting
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
