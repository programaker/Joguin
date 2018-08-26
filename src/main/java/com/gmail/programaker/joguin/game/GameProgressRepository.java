package com.gmail.programaker.joguin.game;

import java.util.Optional;

public interface GameProgressRepository {
    boolean save(GameProgress gameProgress);
    boolean savedProgressExists();
    Optional<GameProgress> restore();
}
