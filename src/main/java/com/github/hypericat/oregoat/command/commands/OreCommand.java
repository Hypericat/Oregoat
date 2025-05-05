package com.github.hypericat.oregoat.command.commands;

import com.github.hypericat.oregoat.Util;
import com.github.hypericat.oregoat.command.BaseOreCommand;
import com.github.hypericat.oregoat.gui.GuiHandler;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;

import java.util.Arrays;
import java.util.List;

public class OreCommand extends BaseOreCommand {
    @Override
    public String getCommandName() {
        return "oregoat";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Util.chat("Current screen before: " + Minecraft.getMinecraft().currentScreen);
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("goat", "ore", "og");
    }
}
