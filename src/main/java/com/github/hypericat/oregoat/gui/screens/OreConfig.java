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

        addDependency("Outline Thickness", "Dungeon Routes Enabled", () -> dungeonRoutes);

    }


    @Switch(
            name = "Dungeon Routes",
            description = "Dungeon routes main toggle",
            category = "Dungeons",
            subcategory = "Routes"
    )
    public static boolean dungeonRoutes = false; // The default value for the boolean Switch.

    @Slider(
            name = "Outline Thickness",
            min = 1f, max = 10f, // Minimum and maximum values for the slider.
            step = 1 // The amount of steps that the slider should have.
    )
    public static float outlineThickness = 1f; // The default value for the float Slider.

}
