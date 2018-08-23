package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.HardcodedLocationRepository;
import com.gmail.programaker.joguin.earth.LocationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public LocationRepository locationRepository() {
        return new HardcodedLocationRepository();
    }
}
