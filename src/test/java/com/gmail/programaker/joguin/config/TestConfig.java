package com.gmail.programaker.joguin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@ComponentScan(basePackages = "com.gmail.programaker.joguin")
public class TestConfig {
    public static final Consumer<String> blackHoleConsole = message -> {};
}
