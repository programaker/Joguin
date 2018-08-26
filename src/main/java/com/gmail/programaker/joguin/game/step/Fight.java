package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.alien.TerraformDevice;
import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.game.progress.GameProgress;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;

import java.util.Iterator;
import java.util.Properties;
import java.util.function.Consumer;

public class Fight {
    private final Properties messages;
    private final Consumer<Long> sleep;
    private Explore explore;

    public Fight(
        Properties messages,
        Consumer<Long> sleep
    ) {
        this.messages = messages;
        this.sleep = sleep;
    }

    //To solve circular dependency between Explore and Fight
    public Fight setExplore(Explore explore) {
        this.explore = explore;
        return this;
    }

    public GameStep start(int selectedInvasion, GameProgress gameProgress) {
        return this.new Step(selectedInvasion, gameProgress);
    }

    private class Step implements GameStep {
        private final int selectedInvasion;
        private final GameProgress gameProgress;

        private Step(int selectedInvasion, GameProgress gameProgress) {
            this.selectedInvasion = selectedInvasion;
            this.gameProgress = gameProgress;
        }

        @Override
        public String name() {
            return Fight.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> print, Iterator<String> playerAnswers) {
            Invasion invasion = gameProgress.getInvasion(selectedInvasion);
            String city = invasion.getCity().getName();

            if (invasion.isAlienDominatedCity()) {
                TerraformDevice device = invasion.getTerraformDevice();
                MainCharacter character = gameProgress.getCharacter();

                print.accept(Messages.get("report", messages,
                    character.getName(),
                    city,
                    device.getDefensePower()
                ));

                String order = AskPlayer.to(Messages.get("give-order", messages),
                    Messages.get("error-invalid-option", messages),
                    print,
                    playerAnswers,
                    String::toLowerCase,
                    this::validateOrder
                );

                if (order.equals("f")) {
                    fight(device, city, print);
                }
            } else {
                print.accept(Messages.get("city-already-saved", messages, city));
            }

            return explore.start(gameProgress);
        }

        private boolean validateOrder(String order) {
            return order.equals("f") || order.equals("r");
        }

        private void fight(TerraformDevice device, String city, Consumer<String> print) {
            int characterExperience = gameProgress.getCharacterExperience();
            int deviceDefensePower = device.getDefensePower();
            boolean deviceDestroyed = characterExperience >= deviceDefensePower;

            showFightAnimation(print);

            gameProgress.increaseCharacterExperience(deviceDefensePower/2);
            if (deviceDestroyed) {
                gameProgress.defeatInvasion(selectedInvasion);
            }

            int newExperience = gameProgress.getCharacterExperience();
            String fightOutcome = deviceDestroyed
                ? Messages.get("earth-won", messages, newExperience)
                : Messages.get("aliens-won", messages, city, newExperience);

            print.accept(fightOutcome);
        }

        private void showFightAnimation(Consumer<String> print) {
            String earth = Messages.get("animation-earth", messages);
            String earthWeapon = Messages.get("animation-earth-weapon", messages);
            String alien = Messages.get("animation-alien", messages);
            String alienWeapon = Messages.get("animation-alien-weapon", messages);
            String strike = Messages.get("animation-strike", messages);
            long time = 100L;

            showAttack(earth, earthWeapon, strike, print);
            sleep.accept(time);

            showAttack(alien, alienWeapon, strike, print);
            sleep.accept(time);
        }

        private void showAttack(String attacker, String weapon, String strike, Consumer<String> print) {
            print.accept("\n");
            print.accept(attacker);

            for (int i = 1; i <= 31; i++) {
                if (i%2 == 0) {
                    print.accept(weapon);
                } else {
                    print.accept(" ");
                }

                sleep.accept(50L);
            }

            print.accept(strike);
            print.accept("\n");
        }
    }
}
