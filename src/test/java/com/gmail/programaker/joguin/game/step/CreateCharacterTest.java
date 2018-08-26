package com.gmail.programaker.joguin.game.step;

import com.gmail.programaker.joguin.game.component.TestGameComponents;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gmail.programaker.joguin.util.TestUtil.blackHoleConsole;
import static org.junit.Assert.assertEquals;

public class CreateCharacterTest extends BaseTest {
    private final String askToCreateCharacter = "\nCreate your main character\n";

    private final String askName = "\nName:\n";
    private final String askGender = "\nGender - (F)emale, (M)ale, (O)ther:\n";
    private final String askAge = "\nAge:\n";

    private final String characterCreated = "\nWelcome, commander Michael Poole! Now, you must bring our forces\n" +
        "to the invaded cities and take them back from the Zorblaxians.\n" +
        "Destroy the Terraform Devices and save all life on Earth!\n";

    private final String errorInvalidName = "Invalid name\n";
    private final String errorInvalidGender = "Invalid gender\n";
    private final String errorInvalidAge = "Invalid age. You must be at least 18 to defend Earth\n";

    private CreateCharacter createCharacter;

    public CreateCharacterTest() {
        createCharacter = new TestGameComponents().createCharacter();
    }

    @Test
    public void interactionsWithPlayer() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.start().interactWithPlayer(
            fakeConsole::add,
            Arrays.asList("Michael Poole", "M", "35").iterator()
        );

        int i = 0;
        assertEquals("Should have asked Player to create character", askToCreateCharacter, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's name", askName, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's gender", askGender, fakeConsole.get(i++));
        assertEquals("Should have asked for Character's age", askAge, fakeConsole.get(i++));
        assertEquals("Should have send welcome message", characterCreated, fakeConsole.get(i++));
    }

    @Test
    public void givenValidCharacterData() {
        GameStep nextStep = createCharacter.start().interactWithPlayer(
            blackHoleConsole,
            Arrays.asList("Carol Danvers", "F", "28").iterator()
        );

        assertEquals("Should have accepted the new character data and gone to Explore step", "Explore", nextStep.name());
    }

    @Test
    public void givenInvalidCharacterData() {
        List<String> fakeConsole = new ArrayList<>();

        createCharacter.start().interactWithPlayer(
            fakeConsole::add,
            Arrays.asList(
                //Name attempts
                " ",
                "Michael Poole",

                //Gender attempts
                "7",
                "M",

                //Age attempts
                "15",
                "35"
            ).iterator()
        );

        //It should ask the information to the Player until a valid answer is given
        int i = 0;

        assertEquals(askToCreateCharacter, fakeConsole.get(i++));

        assertEquals(askName, fakeConsole.get(i++));
        assertEquals(errorInvalidName, fakeConsole.get(i++));
        assertEquals(askName, fakeConsole.get(i++));

        assertEquals(askGender, fakeConsole.get(i++));
        assertEquals(errorInvalidGender, fakeConsole.get(i++));
        assertEquals(askGender, fakeConsole.get(i++));

        assertEquals(askAge, fakeConsole.get(i++));
        assertEquals(errorInvalidAge, fakeConsole.get(i++));
        assertEquals(askAge, fakeConsole.get(i++));

        assertEquals(characterCreated, fakeConsole.get(i++));
    }
}