package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.game.*;

import java.util.function.Consumer;

public abstract class GameConfig {
    private final SaveGame saveGame;
    private final Quit quit;
    private final Fight fight;
    private final GameOver gameOver;
    private final Explore explore;
    private final CreateCharacter createCharacter;
    private final ShowIntro showIntro;
    private final Game game;
    private final MessageConfig messageConfig;
    private final RepositoryConfig repositoryConfig;
    private final Consumer<Long> sleep;

    public GameConfig(RepositoryConfig repositoryConfig, Consumer<Long> sleep) {
        this.repositoryConfig = repositoryConfig;
        this.sleep = sleep;
        messageConfig = new MessageConfig();

        LocationRepository locationRepository = repositoryConfig.locationRepository();
        GameProgressRepository gameProgressRepository = repositoryConfig.gameProgressRepository();

        gameOver = new GameOver();

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
            locationRepository,
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

    public final Consumer<Long> sleep() {
        return sleep;
    }

    public final Game game() {
        return game;
    }

    public final MessageConfig messageConfig() {
        return messageConfig;
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

    public final GameOver gameOver() {
        return gameOver;
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
