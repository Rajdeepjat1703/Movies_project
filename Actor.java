package org.example;

public class Actor {
    int id;
    String name;
    String birthYear; // Changed from int to String

    Actor(int id, String name, String birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }
}