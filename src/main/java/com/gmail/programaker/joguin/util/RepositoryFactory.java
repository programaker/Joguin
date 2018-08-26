package com.gmail.programaker.joguin.util;

import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;

public interface RepositoryFactory {
    CityRepository cityRepository();
    GameProgressRepository gameProgressRepository();
}
