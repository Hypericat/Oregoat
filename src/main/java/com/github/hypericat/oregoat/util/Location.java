package com.github.hypericat.oregoat.util;

public enum Location {
    SinglePlayer("Singleplayer"),
    PrivateIsland("Private Island"),
    Garden("The Garden"),
    SpiderDen("Spider's Den"),
    CrimsonIsle("Crimson Isle"),
    TheEnd("The End"),
    GoldMine("Gold Mine"),
    DeepCaverns("Deep Caverns"),
    DwarvenMines("Dwarven Mines"),
    CrystalHollows("Crystal Hollows"),
    FarmingIsland("The Farming Islands"),
    ThePark("The Park"),
    Dungeon("Catacombs"),
    DungeonHub("Dungeon Hub"),
    Hub("Hub"),
    DarkAuction("Dark Auction"),
    JerryWorkshop("Jerry's Workshop"),
    Kuudra("Kuudra"),
    Mineshaft("Mineshaft"),
    Rift("The Rift"),
    Unknown("(Unknown)");

    private String name;

    Location(String name) {
        this.name = name;
    }

    public boolean match(String s) {
        return s.contains(name);
    }

    public String getName() {
        return name;
    }
}
