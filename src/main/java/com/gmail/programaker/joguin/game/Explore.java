package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import com.gmail.programaker.joguin.zorblax.Invasion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class Explore {
    private final MessageSource messages;
    private final Consumer<Long> sleep;
    private final Fight fightStep;
    private final GameOver gameOver;
    private final Quit quitStep;

    @Autowired
    public Explore(
        @Qualifier("ExploreMessages") MessageSource messages,
        Consumer<Long> sleep,
        @Lazy Fight fightStep, //To solve circular dependency
        GameOver gameOver,
        Quit quitStep
    ) {
        this.messages = messages;
        this.sleep = sleep;
        this.fightStep = fightStep;
        this.gameOver = gameOver;
        this.quitStep = quitStep;
    }

    public GameStep start(GameProgress gameProgress) {
        return this.new Step(gameProgress);
    }

    private class Step implements GameStep {
        private final GameProgress gameProgress;

        private Step(GameProgress gameProgress) {
            this.gameProgress = gameProgress;
        }

        @Override
        public String name() {
            return Explore.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            List<Invasion> invasions = gameProgress.getInvasions();
            print.accept("\n");
            for (int i = 0; i < invasions.size(); i++) {
                printInvasion(invasions.get(i), i, print);
            }

            if (gameProgress.allInvasionsDefeated()) {
                print.accept(Messages.get("mission-accomplished", messages));
                sleep.accept(10000L);
                return gameOver;
            }

            String option = AskPlayer.to(Messages.get("where-do-you-want-to-go", messages, 1, invasions.size()),
                Messages.get("error-invalid-option", messages),
                print,
                playerAnswers,
                String::toLowerCase,
                validateOptionFn(invasions.size())
            );

            if (option.equals("q")) {
                return quitStep.start();
            }

            int selectedInvasion = Integer.parseInt(option);
            return fightStep.start(selectedInvasion, gameProgress);
        }

        private void printInvasion(Invasion invasion, int i, Consumer<String> println) {
            Location location = invasion.getLocation();

            String key = invasion.isAlienDominatedLocation()
                ? "alien-dominated-location"
                : "human-dominated-location";

            println.accept(Messages.get(key, messages, i+1, location.getCity(), location.getCountry()));
        }

        private Predicate<String> validateOptionFn(int invasionCount) {
            return option -> !option.trim().equals("") && option.matches("[1-"+ invasionCount +"q]");
        }
    }
}
