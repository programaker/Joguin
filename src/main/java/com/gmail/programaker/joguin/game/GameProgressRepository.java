package com.gmail.programaker.joguin.game;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameProgressRepository {
    boolean save(GameProgress gameProgress);
    boolean savedProgressExists();
    Optional<GameProgress> restore();
}
