package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class ShowIntro {
    private final MessageSource messages;
    private final GameProgressRepository repository;
    private final CreateCharacter createCharacter;
    private final Explore explore;
    private final Quit quit;

    @Autowired
    public ShowIntro(
        @Qualifier("ShowIntroMessages") MessageSource messages,
        GameProgressRepository repository,
        CreateCharacter createCharacter,
        Explore explore,
        Quit quit
    ) {
        this.messages = messages;
        this.repository = repository;
        this.createCharacter = createCharacter;
        this.explore = explore;
        this.quit = quit;
    }

    public GameStep start() {
        return this.new Step();
    }

    private class Step implements GameStep {
        @Override
        public String name() {
            return ShowIntro.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            print.accept(Messages.get("intro", messages));

            boolean savedProgressExists = repository.savedProgressExists();
            String startKey = savedProgressExists ? "start-with-resume" : "start";

            String option = AskPlayer.to(Messages.get(startKey, messages),
                Messages.get("error-invalid-option", messages),
                print,
                playerAnswers,
                String::toLowerCase,
                validateOption(savedProgressExists)
            );

            if (option.equals("n")) {
                return createCharacter.start();
            }

            if (option.equals("r")) {
                return repository.restore()
                    .map(progress -> welcomeBack(progress, print))
                    .orElseGet(createCharacter::start);
            }

            if (option.equals("q")) {
                return quit.start();
            }

            return null; //Very very unlikely to happen
        }

        private GameStep welcomeBack(GameProgress progress, Consumer<String> print) {
            print.accept(Messages.get("welcome-back", messages, progress.getCharacter().getName()));
            return explore.start(progress);
        }

        private Predicate<String> validateOption(boolean savedProgressExists) {
            return savedProgressExists
                ? option -> option.equals("n") || option.equals("r") || option.equals("q")
                : option -> option.equals("n") || option.equals("q");
        }
    }
}
