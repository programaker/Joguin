package com.gmail.programaker.joguin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.Consumer;

@SpringBootApplication
public class JoguinApplication implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        PrintStream out = System.out;
        out.println(">>> YAY!");

        Scanner sc = new Scanner("1\n2\n3\n");
        out.println(sc.next());
        out.println(sc.next());
        out.println(sc.next());

        Consumer<String> askPlayer = System.out::println;
        Iterator<String> playerAnswers = sc;
    }

	public static void main(String[] args) {
		SpringApplication.run(JoguinApplication.class, args);
	}
}
