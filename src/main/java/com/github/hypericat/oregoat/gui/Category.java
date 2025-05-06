package com.github.hypericat.oregoat.gui;

public enum Category {
    GENERAL("General"),
    DUNGEONS("Dungeons");

    public final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
