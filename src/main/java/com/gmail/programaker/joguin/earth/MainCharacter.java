package com.gmail.programaker.joguin.earth;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainCharacter implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final Gender gender;
    private final int age;

    public MainCharacter(String name, Gender gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public enum Gender {
        NONE(""), //Acts like a null Gender (Null Object pattern)
        FEMALE("F"),
        MALE("M"),
        OTHER("O"),
        ;

        public static Gender byCode(String code) {
            return Optional.ofNullable(code)
                .map(c -> gendersByCode.get(c.toUpperCase()))
                .orElse(NONE);
        }

        private final String code;

        Gender(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        //To get a Gender by its code more easily and in O(1) time
        private static final Map<String, Gender> gendersByCode =
            Arrays.stream(Gender.values()).collect(Collectors.toMap(Gender::getCode, Function.identity()));
    }
}
