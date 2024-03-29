package com.gmail.programaker.joguin.util;

import java.text.MessageFormat;
import java.util.Properties;

/** Utility class to simplify reading and formatting messages from a Properties */
public final class Messages {
    public static String get(String key, Properties source, Object... args) {
        return MessageFormat.format(source.getProperty(key), args);
    }

    private Messages(){}
}
