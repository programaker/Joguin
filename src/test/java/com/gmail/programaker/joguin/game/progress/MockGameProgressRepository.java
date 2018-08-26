package com.gmail.programaker.joguin.game.progress;

import java.util.Optional;

public final class MockGameProgressRepository implements GameProgressRepository {
    private final boolean mockSavingError;
    private GameProgress savedProgress;

    public MockGameProgressRepository(boolean mockSavingError) {
        this.mockSavingError = mockSavingError;
    }

    @Override
    public boolean save(GameProgress gameProgress) {
        if (mockSavingError) {
            return false;
        }

        savedProgress = gameProgress;
        return true;
    }

    @Override
    public boolean savedProgressExists() {
        return savedProgress != null;
    }

    @Override
    public Optional<GameProgress> restore() {
        return Optional.ofNullable(savedProgress);
    }
}
