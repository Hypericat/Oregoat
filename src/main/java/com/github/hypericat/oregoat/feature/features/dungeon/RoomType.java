package com.github.hypericat.oregoat.feature.features.dungeon;

import java.util.Arrays;

public enum RoomType {
    NORMAL("NORMAL"),
    ENTRANCE("ENTRANCE"),
    BLOOD("BLOOD"),
    FAIRY("FAIRY"),
    PUZZLE("PUZZLE"),
    TRAP("TRAP"),
    RARE("RARE"),
    CHAMPION("CHAMPION");


    private final String id;

    RoomType(String id) {
        this.id = id;
    }

    public boolean matches(String s) {
        return s.equalsIgnoreCase(id);
    }

    public String getName() {
        return this.name();
    }

    public static RoomType fromName(String name) {
        return Arrays.stream(RoomType.values()).filter(room -> room.matches(name)).findFirst().orElse(null);
    }
}
