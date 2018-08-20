package com.gmail.programaker.joguin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JoguinApplication implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> YAY!");
    }

	public static void main(String[] args) {
		SpringApplication.run(JoguinApplication.class, args);
	}
}
