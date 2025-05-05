package com.github.hypericat.oregoat.command.commands;

import com.github.hypericat.oregoat.Util;
import com.github.hypericat.oregoat.command.BaseOreCommand;
import com.github.hypericat.oregoat.gui.GuiHandler;
import com.github.hypericat.oregoat.gui.OreConfig;
import io.github.moulberry.moulconfig.gui.GuiScreenElementWrapper;
import io.github.moulberry.moulconfig.gui.MoulConfigEditor;
import io.github.moulberry.moulconfig.processor.MoulConfigProcessor;
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
        GuiHandler.getInstance().setScreen(new GuiScreenElementWrapper(new MoulConfigEditor<>(new MoulConfigProcessor<>(new OreConfig()))));
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("goat", "ore", "og");
    }
}
