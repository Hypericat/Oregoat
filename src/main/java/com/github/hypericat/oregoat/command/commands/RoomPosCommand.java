package com.github.hypericat.oregoat.command.commands;

import com.github.hypericat.oregoat.command.BaseOreCommand;
import com.github.hypericat.oregoat.feature.FeatureHandler;
import com.github.hypericat.oregoat.feature.features.Routes;
import com.github.hypericat.oregoat.feature.features.dungeon.UnitRoom;
import com.github.hypericat.oregoat.gui.GuiHandler;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomPosCommand extends BaseOreCommand {
    @Override
    public String getCommandName() {
        return "roomPos";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            Util.chat("Please look at selected block!");
            return;
        }
        BlockPos blockpos = mc.objectMouseOver.getBlockPos();

        UnitRoom room = FeatureHandler.getByClass(Routes.class).getRoomFromPos(blockpos);
        if (room == null) return;


        Util.chat("Block Room Pos : " + room.toLocalPosition(blockpos));
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<>();
    }
}
