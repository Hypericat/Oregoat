package com.github.hypericat.oregoat;


import com.github.hypericat.oregoat.command.CommandHandler;
import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.ForgeEvents;
import com.github.hypericat.oregoat.feature.FeatureHandler;
import com.github.hypericat.oregoat.feature.features.dungeon.DungeonRoomHandler;
import com.github.hypericat.oregoat.gui.GuiHandler;
import com.github.hypericat.oregoat.util.StateUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;


@Mod(modid = "oregoat", useMetadata=true)
public class Oregoat {

    public static final String MODID = "oregoat";
    public static final String NAME = "Oregoat";


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        StateUtil.init();
        DungeonRoomHandler.initCores();
        EventHandler.registerForgeEvents();
        GuiHandler.init();
        FeatureHandler.initFeatures();
        CommandHandler.initCommands();
        FeatureHandler.debug();
    }
}
