package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.HardcodedLocationRepository;
import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.game.FileGameProgressRepository;
import com.gmail.programaker.joguin.game.GameProgressRepository;

import java.io.File;

public class StandardRepositoryConfig implements RepositoryConfig {
    @Override
    public LocationRepository locationRepository() {
        return new HardcodedLocationRepository();
    }

    @Override
    public GameProgressRepository gameProgressRepository() {
        return new FileGameProgressRepository(new File("saved-game/last-progress.prog"));
    }
}
