package com.github.hypericat.oregoat.command.commands;

import com.github.hypericat.oregoat.util.Util;
import com.github.hypericat.oregoat.command.BaseOreCommand;
import com.github.hypericat.oregoat.gui.GuiHandler;
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
        GuiHandler.getInstance().openConfig();
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("goat", "ore", "og");
    }
}
