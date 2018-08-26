package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.CityRepository;
import com.gmail.programaker.joguin.game.GameProgressRepository;

public interface RepositoryConfig {
    CityRepository cityRepository();
    GameProgressRepository gameProgressRepository();
}
