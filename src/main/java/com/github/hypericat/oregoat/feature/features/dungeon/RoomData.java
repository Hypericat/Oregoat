package com.github.hypericat.oregoat.feature.features.dungeon;

public class RoomData {
    private final String name;
    private final int crypts;
    private final int secrets;
    private final int trappedChests;
    private final int[] cores;
    private final RoomType type;

    public RoomData(String name, int crypts, int secrets, int trappedChests, int[] cores, String type) {
        this.name = name;
        this.crypts = crypts;
        this.secrets = secrets;
        this.trappedChests = trappedChests;
        this.cores = cores;
        this.type = RoomType.fromName(type);
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

    public int[] getCores() {
        return cores;
    }

    public RoomType getType() {
        return type;
    }

    public int getTrappedChests() {
        return this.trappedChests;
    }
}

