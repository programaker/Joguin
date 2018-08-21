package com.gmail.programaker.joguin.game;

import com.gmail.programaker.joguin.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CreateCharacterTest {
    private final Consumer<String> blackHoleConsole = message -> {};

    private final String askToCreateOrQuit = "(C)reate character. (Q)uit";
    private final String askName = "Name:";
    private final String askGender = "Gender - (F)emale, (M)ale, (O)ther:";
    private final String askAge = "Age:";
    private final String errorInvalidChoice = "Invalid choice";
    private final String errorInvalidName = "Invalid name";
    private final String errorInvalidGender = "Invalid gender";
    private final String errorInvalidAge = "Invalid age. You must be at least 18 to fight for Earth";

    @Autowired
    private CreateCharacterMessages messages;

    private CreateCharacter createCharacter;

    @Before
    public void setup() {
        createCharacter = new CreateCharacter(messages);
    }

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.interactWithPlayer(
            fakeConsole::add,
            playerAnswers("C", "Michael Poole", "M", "35")
        );

        int i = 0;
        assertEquals("Should have asked Player to create character or quit", askToCreateOrQuit, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's name", askName, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's gender", askGender, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's age", askAge, fakeConsole.get(i++));
    }

    @Test
    public void givenValidCharacterData() {
        GameStep nextStep = createCharacter.interactWithPlayer(
            blackHoleConsole,
            playerAnswers("C", "Kitana", "F", "28")
        );

        assertTrue("Should have accepted the new character data and gone to Explore step", nextStep instanceof Explore);
    }

    @Test
    public void givenInvalidCharacterData() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.interactWithPlayer(
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
        GameStep nextStep = createCharacter.interactWithPlayer(
            blackHoleConsole,
            Collections.singletonList("Q").iterator()
        );

        assertTrue("Should have gone to Quit step", nextStep instanceof Quit);
    }

    private Iterator<String> playerAnswers(String choice, String charName, String charGender, String charAge) {
        return Arrays.asList(choice, charName, charGender, charAge).iterator();
    }
}