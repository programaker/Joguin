package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.Location;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import com.gmail.programaker.joguin.zorblax.Invasion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Component
public class Explore {
    private final MessageSource messages;
    private final Fight fightStep;
    private final Quit quitStep;

    @Autowired
    public Explore(
        @Qualifier("ExploreMessages")
        MessageSource messages,

        Fight fightStep,
        Quit quitStep
    ) {
        this.messages = messages;
        this.fightStep = fightStep;
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
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            println.accept("");
            List<Invasion> invasions = gameProgress.getInvasions();

            for (int i = 0; i < invasions.size(); i++) {
                printInvasion(invasions.get(i), i, println);
            }

            String option = AskPlayer.to(Messages.get("where-do-you-want-to-go", messages, 1, invasions.size()),
                Messages.get("error-invalid-option", messages),
                println,
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

            String key = invasion.isAliensDefeated()
                ? "human-dominated-location"
                : "alien-dominated-location";

            println.accept(Messages.get(key, messages, i+1, location.getCity(), location.getCountry()));
        }

        private Predicate<String> validateOptionFn(int invasionCount) {
            return option -> !option.trim().equals("") && option.matches("[1-"+ invasionCount +"q]");
        }
    }
}
