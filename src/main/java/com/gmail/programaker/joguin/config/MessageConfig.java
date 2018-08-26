package com.gmail.programaker.joguin.config;

import java.util.Locale;
import java.util.Properties;

public class MessageConfig {
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

            source.load(getClass()
                .getClassLoader()
                .getResourceAsStream(filename)
            );

            return source;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
