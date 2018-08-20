package com.gmail.programaker.joguin.character;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Gender {
    FEMALE("F"),
    MALE("M"),
    OTHER("O"),
    ;

    public static Optional<Gender> byCode(String code) {
        return Optional.ofNullable(code).map(c -> gendersByCode.get(c.toUpperCase()));
    }

    private final String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String,Gender> gendersByCode =
        Arrays.stream(Gender.values()).collect(Collectors.toMap(Gender::getCode, Function.identity()));
}
