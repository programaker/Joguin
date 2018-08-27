package com.gmail.programaker.joguin.game.component;

import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.Game;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;
import com.gmail.programaker.joguin.game.step.*;
import com.gmail.programaker.joguin.util.MessageFactory;
import com.gmail.programaker.joguin.util.RepositoryFactory;

import java.util.function.Consumer;

/** Creates and makes game components (game steps, factories, etc) available to other classes.
 *
 * It is abstract to force the creation of derived classes that are meant to hide
 * concrete dependencies (and their changes) from the external world
 * (like repository factories, sleep function, etc)
 * */
public abstract class GameComponents {
    private final SaveGame saveGame;
    private final Quit quit;
    private final Fight fight;
    private final Explore explore;
    private final CreateCharacter createCharacter;
    private final ShowIntro showIntro;
    private final Game game;
    private final RepositoryFactory repositoryFactory;

    public GameComponents(RepositoryFactory repositoryFactory, Consumer<Long> sleep) {
        this.repositoryFactory = repositoryFactory;

        MessageFactory messageFactory = new MessageFactory();
        CityRepository cityRepository = repositoryFactory.cityRepository();
        GameProgressRepository gameProgressRepository = repositoryFactory.gameProgressRepository();

        GameOver gameOver = new GameOver();

        saveGame = new SaveGame(
            messageFactory.saveGameMessages(),
            gameProgressRepository,
            gameOver
        );

        quit = new Quit(
            messageFactory.quitMessages(),
            saveGame,
            gameOver
        );

        //These two have a circular dependency =(
        //
        //Luckily, GameComponents hides this dirty little secret
        //from the external world
        fight = new Fight(messageFactory.fightMessages(), sleep);
        explore = new Explore(
            messageFactory.exploreMessages(),
            sleep,
            fight,
            gameOver,
            quit
        );
        fight.setExplore(explore);

        createCharacter = new CreateCharacter(
            messageFactory.createCharacterMessages(),
            cityRepository,
            explore
        );

        showIntro = new ShowIntro(
            messageFactory.showIntroMessages(),
            gameProgressRepository,
            createCharacter,
            explore,
            quit
        );

        game = new Game(showIntro);
    }
    
    public final RepositoryFactory repositoryFactory() {
        return repositoryFactory;
    }

    public final Game game() {
        return game;
    }

    public final SaveGame saveGame() {
        return saveGame;
    }

    public final Quit quit() {
        return quit;
    }

    public final Fight fight() {
        return fight;
    }

    public final Explore explore() {
        return explore;
    }

    public final CreateCharacter createCharacter() {
        return createCharacter;
    }

    public final ShowIntro showIntro() {
        return showIntro;
    }
}
