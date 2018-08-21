package com.gmail.programaker.joguin.game;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class AskPlayer {
    public static <T> T to(
        String question,
        String errorMessage,
        Consumer<String> println,
        Iterator<String> playerAnswers,
        Function<String,T> parseAnswer,
        Predicate<T> validateAnswer
    ) {
        T parsedAnswer = null;
        boolean valid = false;

        do {
            println.accept(question);

            try {
                String answer = playerAnswers.next();
                parsedAnswer = parseAnswer.apply(answer);
                valid = validateAnswer.test(parsedAnswer);
            } catch (Exception e) {
                // valid remains false
            }

            if (!valid) {
                println.accept(errorMessage);
            }
        } while (!valid);

        return parsedAnswer;
    }
}
