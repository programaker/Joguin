package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.earth.MainCharacter;
import com.gmail.programaker.joguin.util.AskPlayer;
import com.gmail.programaker.joguin.util.Messages;
import com.gmail.programaker.joguin.zorblax.Invasion;
import com.gmail.programaker.joguin.zorblax.TerraformDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.function.Consumer;

@Component
public class Fight {
    private final MessageSource messages;

    @Autowired
    public Fight(
        @Qualifier("FightMessages") MessageSource messages
    ) {
        this.messages = messages;
    }

    public GameStep start(int selectedInvasion, GameProgress gameProgress, Explore explore) {
        return this.new Step(selectedInvasion, gameProgress, explore);
    }

    private class Step implements GameStep {
        private final int selectedInvasion;
        private final GameProgress gameProgress;
        private final Explore explore;

        private Step(int selectedInvasion, GameProgress gameProgress, Explore explore) {
            this.selectedInvasion = selectedInvasion;
            this.gameProgress = gameProgress;
            this.explore = explore;
        }

        @Override
        public String name() {
            return Fight.class.getSimpleName();
        }

        @Override
        public GameStep interactWithPlayer(Consumer<String> println, Iterator<String> playerAnswers) {
            Invasion invasion = gameProgress.getInvasion(selectedInvasion);
            TerraformDevice device = invasion.getTerraformDevice();
            MainCharacter character = gameProgress.getCharacter();

            println.accept(Messages.get("report", messages,
                character.getName(),
                invasion.getLocation().getCity(),
                device.getDefensePower()
            ));

            String order = AskPlayer.to(Messages.get("give-order", messages),
                Messages.get("invalid-option", messages),
                println,
                playerAnswers,
                String::toLowerCase,
                this::validateOrder
            );

            if (order.equals("f")) {
                FightOutcome fightOutcome = fight(gameProgress.getCharacterExperience(), device.getDefensePower());
                //-- insert post-fight message here --//
                updateGameProgress(fightOutcome);
            }

            return explore.start(gameProgress);
        }

        private boolean validateOrder(String order) {
            return order.equals("f") || order.equals("r");
        }

        private FightOutcome fight(int characterExperience, int devicePower) {
            boolean deviceDestroyed = characterExperience >= devicePower;
            //-- show fight animation --//
            return new FightOutcome(devicePower, deviceDestroyed);
        }

        private void updateGameProgress(FightOutcome fightOutcome) {
            gameProgress.getInvasion(selectedInvasion).setAlienDominatedLocation(fightOutcome.isDeviceDestroyed());
            gameProgress.increaseCharacterExperience(fightOutcome.getGainedExperience());
        }
    }

    private class FightOutcome {
        private final int gainedExperience;
        private final boolean deviceDestroyed;

        private FightOutcome(int gainedExperience, boolean deviceDestroyed) {
            this.gainedExperience = gainedExperience;
            this.deviceDestroyed = deviceDestroyed;
        }

        private int getGainedExperience() {
            return gainedExperience;
        }

        private boolean isDeviceDestroyed() {
            return deviceDestroyed;
        }
    }
}
