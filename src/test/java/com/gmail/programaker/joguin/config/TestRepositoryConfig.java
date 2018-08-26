package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.game.progress.GameProgressRepository;

public class TestRepositoryConfig extends StandardRepositoryConfig {
    private final boolean mockGameProgressSavingError;
    private final GameProgressRepository gameProgressRepository;

    public TestRepositoryConfig(boolean mockGameProgressSavingError) {
        this.mockGameProgressSavingError = mockGameProgressSavingError;
        this.gameProgressRepository = new MockGameProgressRepository(this.mockGameProgressSavingError);
    }

    @Override
    public GameProgressRepository gameProgressRepository() {
        return gameProgressRepository;
    }
}
