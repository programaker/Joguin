package com.gmail.programaker.joguin.game.progress;

import com.gmail.programaker.joguin.alien.Invasion;
import com.gmail.programaker.joguin.util.BaseTest;
import org.junit.Test;

import java.util.List;

import static com.gmail.programaker.joguin.util.TestUtil.beginProgress;
import static org.junit.Assert.*;

public class GameProgressTest extends BaseTest {
    @Test
    public void getInvasionUsesOneBasedIndex() {
        GameProgress progress = beginProgress();

        int size = progress.getInvasions().size();
        Invasion invasion = progress.getInvasion(size);
        assertEquals("São Paulo", invasion.getCity().getName());

        try {
            progress.getInvasion(0);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void increaseCharacterExperience() {
        GameProgress progress = beginProgress();
        assertEquals(0, progress.getCharacterExperience());

        progress.increaseCharacterExperience(100);
        assertEquals(100, progress.getCharacterExperience());

        progress.increaseCharacterExperience(100);
        assertEquals(200, progress.getCharacterExperience());
    }

    @Test
    public void allInvasionsDefeated() {
        GameProgress progress = beginProgress();
        assertFalse(progress.allInvasionsDefeated());

        int size = progress.getInvasions().size();
        for (int i = 1; i <= size; i++) {
            progress.defeatInvasion(i);
        }

        assertTrue(progress.allInvasionsDefeated());
    }

    @Test
    public void defeatInvasionsDirectlyIsForbidden() {
        GameProgress progress = beginProgress();
        List<Invasion> invasions = progress.getInvasions();

        assertFalse(progress.allInvasionsDefeated());
        for (Invasion i : invasions) {
            i.defeated();
        }
        assertFalse(progress.allInvasionsDefeated());

        try {
            Invasion invasion = invasions.get(0);
            Invasion defeatedInvasion = invasion.defeated();
            invasions.set(0, defeatedInvasion);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}