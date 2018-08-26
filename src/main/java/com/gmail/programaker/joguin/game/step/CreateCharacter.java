package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.alien.InvaderArmy;
import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateCharacter {
    private final Properties messages;
    private final CityRepository cityRepository;
    private final Explore exploreStep;

    public CreateCharacter(
        Properties messages,
        CityRepository cityRepository,
        Explore exploreStep
    ) {
        this.messages = messages;
        this.cityRepository = cityRepository;
        this.exploreStep = exploreStep;
    }

    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public String name() {
            return CreateCharacter.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            print.accept(Messages.get("create-character", messages));

            String name = AskPlayer.to(Messages.get("inform-character-name", messages),
                Messages.get("error-invalid-name", messages),
                print,
                playerAnswers,
                Function.identity(),
                this::validateName
            );

            MainCharacter.Gender gender = AskPlayer.to(Messages.get("inform-character-gender", messages),
                Messages.get("error-invalid-gender", messages),
                print,
                playerAnswers,
                MainCharacter.Gender::byCode,
                this::validateGender
            );

            Integer age = AskPlayer.to(Messages.get("inform-character-age", messages),
                Messages.get("error-invalid-age", messages),
                print,
                playerAnswers,
                Integer::parseInt,
                this::validateAge
            );

            MainCharacter character = new MainCharacter(name, gender, age);
            print.accept(Messages.get("character-created", messages, character.getName()));

            return exploreStep.start(initGameProgress(character));
        }

        private boolean validateName(String name) {
            return !name.trim().isEmpty();
        }

        private boolean validateGender(MainCharacter.Gender gender) {
            return !gender.equals(MainCharacter.Gender.NONE);
        }

        private boolean validateAge(Integer age) {
            return age > 18;
        }

        private GameProgress initGameProgress(MainCharacter character) {
            List<Invasion> invasions = cityRepository.findAll()
                .stream()
                .map(InvaderArmy::invade)
                .collect(Collectors.toList());

            return new GameProgress(character, invasions);
        }
    }
}
