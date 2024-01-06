package com.example.learner.learn.day1;

import lombok.Getter;

@Getter
public class Person {
    private final String name;
    private final int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
