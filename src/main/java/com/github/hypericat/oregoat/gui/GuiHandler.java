package com.github.hypericat.oregoat.gui;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ClientTickEvent;
import io.github.moulberry.moulconfig.gui.MoulConfigEditor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiHandler implements ClientTickEvent {
    private static GuiHandler instance;
    private Minecraft mc = Minecraft.getMinecraft();
    private GuiScreen currentScreen;
    MoulConfigEditor<?> editor = null;

    private GuiHandler() {

    }

    public static void init() {
        instance = new GuiHandler();
        EventHandler.register(ClientTickEvent.class, instance);
    }

    public static GuiHandler getInstance() {
        return instance;
    }

    public void setScreen(GuiScreen screen) {
        this.currentScreen = screen;
    }

    public void closeScreen() {
        this.currentScreen = null;
    }

    @Override
    public void onClientTick() {
        if (mc.thePlayer == null || currentScreen == null) return;
        mc.displayGuiScreen(currentScreen);


    }
}
