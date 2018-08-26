package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.game.GameProgressRepository;

public interface RepositoryConfig {
    LocationRepository locationRepository();
    GameProgressRepository gameProgressRepository();
}
