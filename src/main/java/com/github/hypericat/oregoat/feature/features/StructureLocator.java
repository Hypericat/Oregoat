package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.mixinInterface.IChunk;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.StateUtil;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class StructureLocator extends Feature implements LoadChunkEvent, WorldLoadEvent, RenderLastEvent {

    private BlockPos gDragPos;
    private BlockPos grottoPos;

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

        if (!OreConfig.gdragLairFinder && !OreConfig.grottoFinder) return;


        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (findStructures(chunk, x, y, z)) return;
                }
            }
        }
    }

    public boolean findStructures(Chunk chunk, int x, int y, int z) {
        Block block = ((IChunk) chunk).getBlockLocal(x, y, z);
        if (OreConfig.gdragLairFinder && block == Blocks.stained_hardened_clay) {
            if (chunk.getBlockState(new BlockPos(x, y, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.ORANGE.getMetadata()) return false;
            gDragPos = new BlockPos(chunk.xPosition * 16 + x, y, chunk.zPosition * 16 + z);

            Util.chat("Found Golden Dragon at position : " + gDragPos);
            return true;
        }

        if (OreConfig.grottoFinder && block == Blocks.stained_glass) {
            if (chunk.getBlockState(new BlockPos(x, y, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.MAGENTA.getMetadata()) return false;

            grottoPos = new BlockPos(chunk.xPosition * 16 + x, y, chunk.zPosition * 16 + z);
            Util.chat("Found Fairy Grotto at position : " + grottoPos);
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

    }
}
