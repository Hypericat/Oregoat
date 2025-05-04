package com.github.hypericat.oregoat;


import com.github.hypericat.oregoat.feature.FeatureHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "oregoat", useMetadata=true)
public class Oregoat {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FeatureHandler.initFeatures();
    }
}
