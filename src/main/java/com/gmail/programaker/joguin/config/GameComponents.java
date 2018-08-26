package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.city.CityRepository;
import com.gmail.programaker.joguin.game.*;
import com.gmail.programaker.joguin.game.progress.GameProgressRepository;
import com.gmail.programaker.joguin.game.step.*;

import java.util.function.Consumer;

public abstract class GameComponents {
    private final SaveGame saveGame;
    private final Quit quit;
    private final Fight fight;
    private final Explore explore;
    private final CreateCharacter createCharacter;
    private final ShowIntro showIntro;
    private final Game game;
    private final RepositoryConfig repositoryConfig;

    public GameComponents(RepositoryConfig repositoryConfig, Consumer<Long> sleep) {
        this.repositoryConfig = repositoryConfig;

        MessageConfig messageConfig = new MessageConfig();
        CityRepository cityRepository = repositoryConfig.cityRepository();
        GameProgressRepository gameProgressRepository = repositoryConfig.gameProgressRepository();

        GameOver gameOver = new GameOver();

        saveGame = new SaveGame(
            messageConfig.saveGameMessages(),
            gameProgressRepository,
            gameOver
        );

        quit = new Quit(
            messageConfig.quitMessages(),
            saveGame,
            gameOver
        );

        //These two have a circular dependency =(
        fight = new Fight(messageConfig.fightMessages(), sleep);
        explore = new Explore(
            messageConfig.exploreMessages(),
            sleep,
            fight,
            gameOver,
            quit
        );
        fight.setExplore(explore);

        createCharacter = new CreateCharacter(
            messageConfig.createCharacterMessages(),
            cityRepository,
            explore
        );

        showIntro = new ShowIntro(
            messageConfig.showIntroMessages(),
            gameProgressRepository,
            createCharacter,
            explore,
            quit
        );

        game = new Game(showIntro);
    }
    
    public final RepositoryConfig repositoryConfig() {
        return repositoryConfig;
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
