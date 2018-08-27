package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.earth.city.City;
import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Predicate;

/** Starts an explore step hiding its dependencies from the caller step.
 * This way, the caller step only need to pass its product to the explore */
public class Explore {
    private final Properties messages;
    private final Consumer<Long> sleep;
    private final Fight fight;
    private final GameOver gameOver;
    private final Quit quit;

    public Explore(
        Properties messages,
        Consumer<Long> sleep,
        Fight fight,
        GameOver gameOver,
        Quit quit
    ) {
        this.messages = messages;
        this.sleep = sleep;
        this.fight = fight;
        this.gameOver = gameOver;
        this.quit = quit;
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
                return quit.start(gameProgress);
            }

            int selectedInvasion = Integer.parseInt(option);
            return fight.start(selectedInvasion, gameProgress);
        }

        private void printInvasion(Invasion invasion, int i, Consumer<String> println) {
            City city = invasion.getCity();

            String key = invasion.isAlienDominatedCity()
                ? "alien-dominated-city"
                : "human-dominated-city";

            println.accept(Messages.get(key, messages, i+1, city.getName(), city.getCountry()));
        }

        private Predicate<String> validateOptionFn(int invasionCount) {
            return option -> !option.trim().equals("") && option.matches("[1-"+ invasionCount +"q]");
        }
    }
}
