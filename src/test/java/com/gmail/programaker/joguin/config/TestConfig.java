package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.game.GameProgress;
import com.gmail.programaker.joguin.game.GameProgressRepository;
import com.gmail.programaker.joguin.zorblax.InvaderArmy;
import com.gmail.programaker.joguin.zorblax.Invasion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@ComponentScan(basePackages = "com.gmail.programaker.joguin")
public class TestConfig {
    public static final Consumer<String> blackHoleConsole = message -> {};

    public static final GameProgressRepository emptyGameProgressRepository = new GameProgressRepository() {
        @Override
        public boolean save(GameProgress gameProgress) {
            return true;
        }

        @Override
        public boolean savedProgressExists() {
            return false;
        }

        @Override
        public Optional<GameProgress> restore() {
            return Optional.empty();
        }
    };

    public static final GameProgressRepository fullGameProgressRepository = new GameProgressRepository() {
        @Override
        public boolean save(GameProgress gameProgress) {
            return true;
        }

        @Override
        public boolean savedProgressExists() {
            return true;
        }

        @Override
        public Optional<GameProgress> restore() {
            return Optional.of(beginProgress());
        }
    };

    public static final class MockGameProgressRepository implements GameProgressRepository {
        private final boolean mockSavingError;
        private GameProgress savedProgress;

        public MockGameProgressRepository(boolean mockSavingError) {
            this.mockSavingError = mockSavingError;
        }

        @Override
        public boolean save(GameProgress gameProgress) {
            if (mockSavingError) {
                return false;
            }

            savedProgress = gameProgress;
            return true;
        }

        @Override
        public boolean savedProgressExists() {
            return savedProgress != null;
        }

        @Override
        public Optional<GameProgress> restore() {
            return Optional.ofNullable(savedProgress);
        }
    }

    public static GameProgress beginProgress() {
        MainCharacter character = new MainCharacter("Uhura", MainCharacter.Gender.FEMALE, 36);

        List<Invasion> invasions = Arrays.asList(
            InvaderArmy.invade(new Location("London", "UK")),
            InvaderArmy.invade(new Location("Tokyo", "Japan")),
            InvaderArmy.invade(new Location("São Paulo", "Brazil"))
        );

        return new GameProgress(character, invasions);
    }

    @Bean
    public Consumer<Long> sleep() {
        //Overriding sleep function to accelerate tests
        return millis -> {};
    }
}
