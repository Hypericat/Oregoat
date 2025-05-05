package com.github.hypericat.oregoat.command;

import com.github.hypericat.oregoat.command.commands.OreCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandHandler {
    private CommandHandler() {

    }

    public static void initCommands() {
        ClientCommandHandler handler = ClientCommandHandler.instance;

        handler.registerCommand(new OreCommand());
    }
}