package com.gmail.programaker.joguin.config;

import com.gmail.programaker.joguin.earth.LocationRepository;
import com.gmail.programaker.joguin.game.*;

import java.util.function.Consumer;

public interface GameConfig {
    RepositoryConfig repositoryConfig();
    Consumer<Long> sleep();

    default Game game() {
        MessageConfig mc = messageConfig();
        RepositoryConfig rc = repositoryConfig();
        Consumer<Long> sleep = sleep();

        LocationRepository locationRepository = rc.locationRepository();
        GameProgressRepository gameProgressRepository = rc.gameProgressRepository();

        GameOver gameOver = new GameOver();

        SaveGame saveGame = new SaveGame(
            mc.saveGameMessages(),
            gameProgressRepository,
            gameOver
        );

        Quit quit = new Quit(
            mc.quitMessages(),
            saveGame,
            gameOver
        );

        //These two have a circular dependecy
        Fight fight = new Fight(mc.fightMessages(), sleep);
        Explore explore = new Explore(
            mc.exploreMessages(),
            sleep,
            fight,
            gameOver,
            quit
        );
        fight.setExplore(explore);

        CreateCharacter createCharacter = new CreateCharacter(
            mc.createCharacterMessages(),
            locationRepository,
            explore
        );

        ShowIntro showIntro = new ShowIntro(
            mc.showIntroMessages(),
            gameProgressRepository,
            createCharacter,
            explore,
            quit
        );

        return new Game(showIntro);
    }

    default MessageConfig messageConfig() {
        return new MessageConfig();
    }
}
