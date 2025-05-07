package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.feature.features.dungeon.UnitRoom;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.util.StateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import org.lwjgl.util.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Routes extends Feature implements RenderLastEvent, WorldLoadEvent, ClientTickEvent {

    private List<BlockPos> blocks;
    HashMap<Long, UnitRoom> unitRooms;
    private UnitRoom currentRoom;

    private final Frustum frustum;

    public Routes() {
        this.frustum = new Frustum();
        this.blocks = new ArrayList<>();
        this.unitRooms = new HashMap<>();
        blocks.add(new BlockPos(0, 0, 0));
    }

    @Override
    protected void onEnable() {
        EventHandler.register(RenderLastEvent.class, this);
        EventHandler.register(WorldLoadEvent.class, this);
        EventHandler.register(ClientTickEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
        EventHandler.unregister(WorldLoadEvent.class, this);
        EventHandler.unregister(ClientTickEvent.class, this);
    }

    @Override
    public String getName() {
        return "Route";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"Route", "Secrets"};
    }


    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (currentRoom != null) {
            renderBoxOutline(currentRoom.getBox().to3dCentered(70, 20), partialTicks, new Color(0, 0, 255));
        }

        float eyeHeight = Minecraft.getMinecraft().thePlayer.getEyeHeight();
        BlockPos pos = new BlockPos(0, 72, 0);

        AxisAlignedBB bb = new AxisAlignedBB(pos, pos.add(1, 1, 1));

        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        frustum.setPosition(player.posX, player.posY, player.posZ);
        if (!frustum.isBoundingBoxInFrustum(bb)) return; // Dont return for tracer

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        bb = bb.offset(-playerX, -playerY, -playerZ);

        RenderUtil.drawOutlinedBoundingBox(bb, new Color(0, 255, 0), 1f);
        RenderUtil.drawTracer(RenderUtil.getBBCenter(bb), eyeHeight, new Color(0, 255, 0), 1f);
        //RenderUtil.drawFilledBoundingBox(bb, new Color(0, 0, 255), 0.8f);
    }

    @Override
    public void onWorldLoad(World world) {
        System.out.println("On World Load!");
        unitRooms.clear();
        for (int x = 0; x < 6; x++) {
            for (int z = 0; z < 6; z++) {
                unitRooms.put(encodeIndex(x, z), new UnitRoom(new BlockPos((-25 - (32 * x)), 70, (-25 - (32 * z)))));
                //System.out.println("Added room! at : " + unitRooms.get(unitRooms.size() - 1).getBox());
            }
        }

    }

    public void renderBoxOutline(AxisAlignedBB bb, float partialTicks, Color color) {
        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        frustum.setPosition(player.posX, player.posY, player.posZ);
        if (!frustum.isBoundingBoxInFrustum(bb)) return;

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        bb = bb.offset(-playerX, -playerY, -playerZ);

        RenderUtil.drawOutlinedBoundingBox(bb, color, 1f);
    }

    public long encodeIndex(int x, int z) {
        return (long) x | (((long) z) << 32);
    }

    public long encodeIndex(Point p) {
        return encodeIndex(p.x, p.y);
    }


    public Point coordToIndexPoint(BlockPos pos) {
        Point point = new Point();
        point.x = (-10 - pos.getX()) / 32;
        point.y = (-10 - pos.getZ()) / 32;
        return point;
    }

    @Override
    public void onClientTick() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || Minecraft.getMinecraft().theWorld == null) return;

        Point pos = coordToIndexPoint(player.getPosition());
        currentRoom = unitRooms.get(encodeIndex(pos));


    }
}
