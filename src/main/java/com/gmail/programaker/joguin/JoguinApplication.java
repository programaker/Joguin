package com.gmail.programaker.joguin;

import com.gmail.programaker.joguin.game.Game;
import com.gmail.programaker.joguin.game.GameStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class JoguinApplication implements CommandLineRunner {
    private final Game game;

    @Autowired
    public JoguinApplication(Game game) {
        this.game = game;
    }

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
            GameStep step = game.start();

            while (!step.gameOver()) {
                step = step.interactWithPlayer(System.out::println, scanner);
            }
        }
    }

	public static void main(String[] args) {
		SpringApplication.run(JoguinApplication.class, args);
	}
}
