package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.mixinInterface.IChunk;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.util.StateUtil;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.lwjgl.Sys;
import org.lwjgl.util.Color;

public class StructureLocator extends Feature implements LoadChunkEvent, WorldLoadEvent, RenderLastEvent {

    private BlockPos gDragPos;
    private BlockPos grottoPos;

    public static final Color gdragColor = new Color(0xB7, 0x87, 0x27);
    public static final Color grottoColor = new Color(213, 11, 227);

    @Override
    protected void onEnable() {
        EventHandler.register(LoadChunkEvent.class, this);
        EventHandler.register(WorldLoadEvent.class, this);
        EventHandler.register(RenderLastEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(LoadChunkEvent.class, this);
        EventHandler.unregister(WorldLoadEvent.class, this);
        EventHandler.unregister(RenderLastEvent.class, this);
    }

    @Override
    public String getName() {
        return "StructureLocator";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public void onLoadChunk(Chunk chunk) {
        if (StateUtil.getLocation() != Location.CrystalHollows) return;

        if (!shouldRunCheck()) return;

        //System.out.println("Load Chunk!");

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    // Should run check is only called if we actually find something because the && operator only evaluates the second term if the first is true
                    if (findStructures(chunk, x, y, z) && !shouldRunCheck()) return;
                }
            }
        }
    }

    public boolean shouldRunCheck() {
        return (OreConfig.gdragLairFinder && gDragPos == null) || (OreConfig.grottoFinder && grottoPos == null);
    }

    public boolean findStructures(Chunk chunk, int x, int y, int z) {
        Block block = ((IChunk) chunk).getBlockLocal(x, y, z);
        //System.out.println("Test Block! : " + block);

        if (OreConfig.gdragLairFinder && block == Blocks.stained_hardened_clay && gDragPos == null) {
            if (chunk.getBlockState(new BlockPos(x, y, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.ORANGE.getMetadata()) return false;
            if (chunk.getBlockState(new BlockPos(x, y + 1, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.RED.getMetadata()) return false;

            gDragPos = new BlockPos((chunk.xPosition << 4) + x, y, (chunk.zPosition << 4) + z);
            Util.chatOfficial("Found &&l&&6Golden Dragon &&r&&9at " + Util.blockPosToString(gDragPos));
            return true;
        }

        if (OreConfig.grottoFinder && block == Blocks.stained_glass && grottoPos == null) {
            if (chunk.getBlockState(new BlockPos(x, y, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.MAGENTA.getMetadata()) return false;

            grottoPos = new BlockPos((chunk.xPosition << 4) + x, y, (chunk.zPosition << 4) + z);
            Util.chatOfficial("Found &&l&&dFairy Grotto &&r&&9at " + Util.blockPosToString(grottoPos));
            return true;
        }

        return false;
    }

    @Override
    public void onWorldLoad(World world) {
        gDragPos = null;
        grottoPos = null;
    }

    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (gDragPos == null && grottoPos == null) return;

        if (OreConfig.gdragLairFinder && OreConfig.gdragWaypoint && gDragPos != null) {
            RenderUtil.renderBlockOutlineTracer(gDragPos, partialTicks, gdragColor);
        }

        if (OreConfig.grottoFinder && OreConfig.grottoWaypoint && grottoPos != null) {
            RenderUtil.renderBlockOutlineTracer(grottoPos, partialTicks, grottoColor);
        }
    }
}
