package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;

public interface RepositoryConfig {
    CityRepository cityRepository();
    GameProgressRepository gameProgressRepository();
}
