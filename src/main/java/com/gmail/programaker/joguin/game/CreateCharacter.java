package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.zorblax.InvaderArmy;
import com.gmail.programaker.joguin.zorblax.Invasion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CreateCharacter {
    private final MessageSource messages;
    private final LocationRepository locationRepository;
    private final Explore exploreStep;

    @Autowired
    public CreateCharacter(
        @Qualifier("CreateCharacterMessages")
        MessageSource messages,

        LocationRepository locationRepository,
        Explore exploreStep
    ) {
        this.messages = messages;
        this.locationRepository = locationRepository;
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
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            println.accept(Messages.get("create-character", messages));

            String name = AskPlayer.to(Messages.get("inform-character-name", messages),
                Messages.get("error-invalid-name", messages),
                println,
                playerAnswers,
                Function.identity(),
                this::validateName
            );

            MainCharacter.Gender gender = AskPlayer.to(Messages.get("inform-character-gender", messages),
                Messages.get("error-invalid-gender", messages),
                println,
                playerAnswers,
                MainCharacter.Gender::byCode,
                this::validateGender
            );

            Integer age = AskPlayer.to(Messages.get("inform-character-age", messages),
                Messages.get("error-invalid-age", messages),
                println,
                playerAnswers,
                Integer::parseInt,
                this::validateAge
            );

            MainCharacter character = new MainCharacter(name, gender, age);
            println.accept(Messages.get("character-created", messages, character.getName()));

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
            List<Invasion> invasions = locationRepository.findAll()
                .stream()
                .map(InvaderArmy::invade)
                .collect(Collectors.toList());

            return new GameProgress(character, invasions);
        }
    }
}
