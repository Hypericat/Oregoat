package com.github.hypericat.oregoat.gui.screens;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Dropdown;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import com.github.hypericat.oregoat.Oregoat;

public class OreConfig extends Config {

    public OreConfig() {
        super(new Mod(Oregoat.NAME, ModType.SKYBLOCK), Oregoat.MODID + ".json");
        initialize();

        addDependency("Show Gdrag Lair Waypoint", "Gdrag Finder Enabled", () -> gdragLairFinder);
        addDependency("Show Grotto Waypoint", "Grotto Finder Enabled", () -> grottoFinder);

    }


    @Slider(
            name = "Render Line Thickness",
            min = 1f, max = 10f, // Minimum and maximum values for the slider.
            step = 1 // The amount of steps that the slider should have.
    )
    public static float outlineThickness = 1f; // The default value for the float Slider.



    @Switch(
            name = "Dungeon Routes",
            description = "Dungeon routes main toggle",
            category = "Dungeons",
            subcategory = "Routes"
    )
    public static boolean dungeonRoutes = false; // The default value for the boolean Switch.


    @Switch(
            name = "Gdrag Lair Finder",
            description = "Finds Golden Dragon lairs from chunk data",
            category = "Mining",
            subcategory = "Crystal Hollows"
    )
    public static boolean gdragLairFinder = true;

    @Switch(
            name = "Show Gdrag Lair Waypoint",
            category = "Mining",
            subcategory = "Crystal Hollows"
    )
    public static boolean gdragWaypoint = true;



    @Switch(
            name = "Grotto Finder",
            description = "Finds Fairy Grottos from chunk data",
            category = "Mining",
            subcategory = "Crystal Hollows"
    )
    public static boolean grottoFinder = true;

    @Switch(
            name = "Show Grotto Waypoint",
            category = "Mining",
            subcategory = "Crystal Hollows"
    )
    public static boolean grottoWaypoint = true;
}
