package com.gmail.programaker.joguin.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
    @Bean
    @Qualifier("CreateCharacterMessages")
    public MessageSource createCharacterMessages() {
        return messageSource("CreateCharacterMessages");
    }

    @Bean
    @Qualifier("ExploreMessages")
    public MessageSource exploreMessages() {
        return messageSource("ExploreMessages");
    }

    @Bean
    @Qualifier("QuitMessages")
    public MessageSource quitMessages() {
        return messageSource("QuitMessages");
    }

    @Bean
    @Qualifier("ShowIntroMessages")
    public MessageSource showIntroMessages() {
        return messageSource("ShowIntroMessages");
    }

    @Bean
    @Qualifier("FightMessages")
    public MessageSource fightMessages() {
        return messageSource("FightMessages");
    }

    @Bean
    @Qualifier("SaveGameMessages")
    public MessageSource saveGameMessages() {
        return messageSource("SaveGameMessages");
    }

    private MessageSource messageSource(String basename) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
