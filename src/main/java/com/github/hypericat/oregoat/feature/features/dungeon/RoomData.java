package com.github.hypericat.oregoat.feature.features.dungeon;

import java.util.List;

public class RoomData {
    private final String name;
    private final int crypts;
    private final int secrets;
    private final int trappedChests;
    private final List<Integer> cores;
    private final RoomType type;

    public RoomData(String name, int crypts, int secrets, int trappedChests, List<Integer> cores, String type) {
        this.name = name;
        this.crypts = crypts;
        this.secrets = secrets;
        this.trappedChests = trappedChests;
        this.cores = cores;
        this.type = RoomType.fromName(type);
        if (type == null) {
            System.err.println("NULL ROOM : " + type);
        }
    }

    public String getName() {
        return name;
    }

    public int getCrypts() {
        return crypts;
    }

    public int getSecrets() {
        return secrets;
    }

    public List<Integer> getCores() {
        return cores;
    }

    public RoomType getType() {
        return type;
    }

    public int getTrappedChests() {
        return this.trappedChests;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Name : " + name).append(", Crypts : " + crypts).append(", Secrets : " + secrets).append(", TrappedChests : " + trappedChests).append(", Type : " + type.getName());
        builder.append(", Core(s) : ");
        cores.forEach(core -> builder.append(", " + core));
        return builder.toString();
    }
}

