package com.gmail.programaker.joguin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@ComponentScan(basePackages = "com.gmail.programaker.joguin")
public class TestConfig {
    public static final Consumer<String> blackHoleConsole = message -> {};

    @Bean
    public Consumer<Long> sleep() {
        //Overriding sleep function to accelerate tests
        return millis -> {};
    }
}
