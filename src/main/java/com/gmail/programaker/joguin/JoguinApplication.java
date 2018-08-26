package com.gmail.programaker.joguin;

import com.gmail.programaker.joguin.config.GameConfig;
import com.gmail.programaker.joguin.config.StandardGameConfig;
import com.gmail.programaker.joguin.game.Game;
import com.gmail.programaker.joguin.game.GameStep;

import java.util.Scanner;

public class JoguinApplication {
    public static void main(String[] args) {
        GameConfig config = new StandardGameConfig();
        Game game = config.game();

        try (Scanner scanner = new Scanner(System.in, "UTF-8").useDelimiter("\n")) {
            GameStep nextStep = game.start();

            while (!nextStep.gameOver()) {
                nextStep = nextStep.interactWithPlayer(System.out::print, scanner);
            }
        }
    }
}
