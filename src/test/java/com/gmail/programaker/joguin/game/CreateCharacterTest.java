package com.gmail.programaker.joguin.game;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class CreateCharacterTest {
    private final Consumer<String> askPlayerMock = message -> {};

    @Test
    public void whenCharacterDataIsValid() {
        //Player chooses to create a character (c)
        GameStep nextStep = new CreateCharacter().interactWithPlayer(
            askPlayerMock,
            playerAnswers("Paul Atreides", "m", "15")
        );

        assertTrue("Should have accepted the new character data and gone to Explore step", nextStep instanceof Explore);
    }

    @Test
    public void whenCharacterDataIsInvalid() {
        //Player chooses to create a character (c)
        GameStep nextStep = new CreateCharacter().interactWithPlayer(
            askPlayerMock,
            playerAnswers("Lady Jessica", "7", "32") //Invalid Gender: 7
        );

        assertTrue("Should have repeated the CreateCharacter step in case of error", nextStep instanceof CreateCharacter);
    }

    @Test
    public void whenPlayerAsksToQuit() {
        //Player chooses to quit (q)
        GameStep nextStep = new CreateCharacter().interactWithPlayer(
            askPlayerMock,
            Collections.singletonList("q").iterator()
        );

        assertTrue("Should have gone to Quit step", nextStep instanceof Quit);
    }

    private Iterator<String> playerAnswers(String charName, String charGender, String charAge) {
        return Arrays.asList("c", charName, charGender, charAge).iterator();
    }
}