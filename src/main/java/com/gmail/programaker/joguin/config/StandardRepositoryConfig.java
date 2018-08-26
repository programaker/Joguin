package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.city.HardcodedCityRepository;
import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.progress.FileGameProgressRepository;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;

import java.io.File;

public class StandardRepositoryConfig implements RepositoryConfig {
    @Override
    public CityRepository cityRepository() {
        return new HardcodedCityRepository();
    }

    @Override
    public GameProgressRepository gameProgressRepository() {
        return new FileGameProgressRepository(new File("saved-game/last-progress.prog"));
    }
}
