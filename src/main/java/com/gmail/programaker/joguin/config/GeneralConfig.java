package com.gmail.programaker.joguin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class GeneralConfig {
    @Bean
    public Consumer<Long> sleep() {
        return millis -> {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                // ¯\_(ツ)_/¯
            }
        };
    }
}
