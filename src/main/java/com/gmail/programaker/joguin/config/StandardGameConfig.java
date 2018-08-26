package com.gmail.programaker.joguin.config;

public class StandardGameConfig extends GameConfig {
    public StandardGameConfig() {
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
