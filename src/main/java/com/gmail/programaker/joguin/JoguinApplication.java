package com.gmail.programaker.joguin;

import com.gmail.programaker.joguin.game.Game;
import com.gmail.programaker.joguin.game.component.GameComponents;
import com.gmail.programaker.joguin.game.component.StandardGameComponents;
import com.gmail.programaker.joguin.game.step.GameStep;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class JoguinApplication {
    public static void main(String[] args) {
        GameComponents gameComponents = new StandardGameComponents();
        Game game = gameComponents.game();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name()).useDelimiter("\n")) {
            GameStep nextStep = game.start();

            //The game loop
            while (!nextStep.gameOver()) {
                //Using System.out.print as Consumer<String> to send messages to the player via console
                //and Scanner as Iterator<String> to read player's messages from console
                nextStep = nextStep.interactWithPlayer(System.out::print, scanner);
            }
        }
    }
}
