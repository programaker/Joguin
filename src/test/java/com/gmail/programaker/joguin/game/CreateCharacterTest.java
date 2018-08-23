package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.config.MessageConfig;
import com.gmail.programaker.joguin.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static com.gmail.programaker.joguin.config.TestConfig.blackHoleConsole;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, MessageConfig.class})
public class CreateCharacterTest {
    private final String askToCreateOrQuit = "\n(C)reate character. (Q)uit";
    private final String askName = "\nName:";
    private final String askGender = "\nGender - (F)emale, (M)ale, (O)ther:";
    private final String askAge = "\nAge:";
    private final String errorInvalidChoice = "Invalid option";
    private final String errorInvalidName = "Invalid name";
    private final String errorInvalidGender = "Invalid gender";
    private final String errorInvalidAge = "Invalid age. You must be at least 18 to fight for Earth";

    @Autowired
    private CreateCharacter createCharacter;

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.start().interactWithPlayer(
            fakeConsole::add,
            Arrays.asList("C", "Michael Poole", "M", "35").iterator()
        );

        int i = 0;
        assertEquals("Should have asked Player to create character or quit", askToCreateOrQuit, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's name", askName, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's gender", askGender, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's age", askAge, fakeConsole.get(i++));
    }

    @Test
    public void givenValidCharacterData() {
        GameStep nextStep = createCharacter.start().interactWithPlayer(
            blackHoleConsole,
            Arrays.asList("C", "Kitana", "F", "28").iterator()
        );

        assertEquals("Should have accepted the new character data and gone to Explore step", "Explore", nextStep.name());
    }

    @Test
    public void givenInvalidCharacterData() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.start().interactWithPlayer(
            fakeConsole::add,
            Arrays.asList(
                //Choice attempts
                "A",
                "C",

                //Name attempts
                " ",
                "Lady Jay",

                //Gender attempts
                "7",
                "F",

                //Age attempts
                "15",
                "25"
            ).iterator()
        );

        //It should ask the information to the Player until a valid answer is given
        int i = 0;

        assertEquals(askToCreateOrQuit, fakeConsole.get(i++));
        assertEquals(errorInvalidChoice, fakeConsole.get(i++));
        assertEquals(askToCreateOrQuit, fakeConsole.get(i++));

        assertEquals(askName, fakeConsole.get(i++));
        assertEquals(errorInvalidName, fakeConsole.get(i++));
        assertEquals(askName, fakeConsole.get(i++));

        assertEquals(askGender, fakeConsole.get(i++));
        assertEquals(errorInvalidGender, fakeConsole.get(i++));
        assertEquals(askGender, fakeConsole.get(i++));

        assertEquals(askAge, fakeConsole.get(i++));
        assertEquals(errorInvalidAge, fakeConsole.get(i++));
        assertEquals(askAge, fakeConsole.get(i++));
    }

    @Test
    public void whenThePlayerAsksToQuit() {
        GameStep nextStep = createCharacter.start().interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertEquals("Should have gone to Quit step", "Quit", nextStep.name());
    }
}