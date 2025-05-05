package com.github.hypericat.oregoat.gui;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ClientTickEvent;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiHandler {
    private static GuiHandler instance;
    private Minecraft mc = Minecraft.getMinecraft();
    private final OreConfig config = new OreConfig();

    private GuiHandler() {

    }

    public static void init() {
        instance = new GuiHandler();
    }

    public static GuiHandler getInstance() {
        return instance;
    }

    public void openConfig() {
        config.openGui();
    }
}
