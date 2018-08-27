package com.gmail.programaker.joguin.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Properties;

/** Creates the message properties for the game steps */
public class MessageFactory {
    //This opens the possibility of internationalization,
    //but it only knows english for now
    private final Locale locale = Locale.US;

    public Properties createCharacterMessages() {
        return messageSource("CreateCharacterMessages");
    }

    public Properties exploreMessages() {
        return messageSource("ExploreMessages");
    }

    public Properties quitMessages() {
        return messageSource("QuitMessages");
    }

    public Properties showIntroMessages() {
        return messageSource("ShowIntroMessages");
    }

    public Properties fightMessages() {
        return messageSource("FightMessages");
    }

    public Properties saveGameMessages() {
        return messageSource("SaveGameMessages");
    }

    private Properties messageSource(String basename) {
        try {
            Properties source = new Properties();
            String filename = basename + "_" + locale + ".properties";

            InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(filename);

            source.load(new InputStreamReader(is, StandardCharsets.UTF_8));
            return source;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
