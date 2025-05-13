package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.feature.features.dungeon.DungeonRoomHandler;
import com.github.hypericat.oregoat.feature.features.dungeon.UnitRoom;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.util.StateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.util.Color;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GdragLocator extends Feature implements ReceivePacketEvent {


    @Override
    public void onReceivePacket(Packet<?> packet, CallbackInfo ci) {
        // S26PacketMapChunkBulk
        if (packet instanceof S21PacketChunkData) {
            S21PacketChunkData chunkPacket = (S21PacketChunkData) packet;
            byte[] data = chunkPacket.getExtractedDataBytes();
            int size = chunkPacket.getExtractedSize();
            handleChunk(data, size, chunkPacket.getChunkX(), chunkPacket.getChunkZ());

        }
    }

    public void handleChunk(byte[] data, int size, int chunkX, int chunkZ) {

    }


    @Override
    protected void onEnable() {
        EventHandler.register(ReceivePacketEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(ReceivePacketEvent.class, this);
    }

    @Override
    public String getName() {
        return "GdragLocator";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }
}
