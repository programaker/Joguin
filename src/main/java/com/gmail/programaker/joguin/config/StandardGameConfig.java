package com.gmail.programaker.joguin.config;

import java.util.function.Consumer;

public class StandardGameConfig implements GameConfig {
    @Override
    public RepositoryConfig repositoryConfig() {
        return new StandardRepositoryConfig();
    }

    @Override
    public Consumer<Long> sleep() {
        return millis -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // ¯\_(ツ)_/¯
            }
        };
    }
}
