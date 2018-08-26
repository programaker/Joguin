package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.HardcodedLocationRepository;
import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.game.FileGameProgressRepository;
import com.gmail.programaker.joguin.game.GameProgressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class RepositoryConfig {
    @Bean
    public LocationRepository locationRepository() {
        return new HardcodedLocationRepository();
    }

    @Bean
    public GameProgressRepository gameProgressRepository() {
        return new FileGameProgressRepository(new File("saved-game/last-progress.prog"));
    }
}
