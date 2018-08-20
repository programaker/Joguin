package com.gmail.programaker.joguin.character;

public class Character {
    private long id;
    private String name;
    private Gender gender;
    private int age;

    public long getId() {
        return id;
    }

    public Character setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Character setName(String name) {
        this.name = name;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Character setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Character setAge(int age) {
        this.age = age;
        return this;
    }
}
