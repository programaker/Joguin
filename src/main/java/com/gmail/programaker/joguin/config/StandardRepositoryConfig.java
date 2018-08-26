package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.HardcodedCityRepository;
import com.gmail.programaker.joguin.earth.CityRepository;
import com.gmail.programaker.joguin.game.FileGameProgressRepository;
import com.gmail.programaker.joguin.game.GameProgressRepository;

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
