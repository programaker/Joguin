package com.gmail.programaker.joguin.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

public final class Messages {
    public static String get(String key, MessageSource source, Object... args) {
        return source.getMessage(key, args, Locale.getDefault());
    }

    private Messages(){}
}
