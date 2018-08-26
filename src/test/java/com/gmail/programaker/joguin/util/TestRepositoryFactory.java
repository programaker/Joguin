package com.gmail.programaker.joguin.util;

import com.gmail.programaker.joguin.game.progress.GameProgressRepository;
import com.gmail.programaker.joguin.game.progress.MockGameProgressRepository;

public class TestRepositoryFactory extends StandardRepositoryFactory {
    private final boolean mockGameProgressSavingError;
    private final GameProgressRepository gameProgressRepository;

    public TestRepositoryFactory(boolean mockGameProgressSavingError) {
        this.mockGameProgressSavingError = mockGameProgressSavingError;
        this.gameProgressRepository = new MockGameProgressRepository(this.mockGameProgressSavingError);
    }

    @Override
    public GameProgressRepository gameProgressRepository() {
        return gameProgressRepository;
    }
}
