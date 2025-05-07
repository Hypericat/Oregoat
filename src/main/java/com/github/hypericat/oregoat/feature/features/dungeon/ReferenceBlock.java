package com.github.hypericat.oregoat.feature.features.dungeon;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class ReferenceBlock {
    BlockPos pos;
    Block block;

    public ReferenceBlock(BlockPos pos, Block block) {

    }

    public boolean satisfies(UnitRoom room) {
        return Minecraft.getMinecraft().theWorld.getBlockState(room.toAbsolutePosition(pos)) == block;
    }

}
