package com.github.hypericat.oregoat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

public class Util {
    public static void chat(@NotNull String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message.replaceAll("&&", "§")));
    }

    public static void chatOfficial(@NotNull String message) {
        chat("§4[Oregoat]§9 " + message);
    }

    public static String blockPosToString(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }
}
