package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.feature.features.dungeon.DungeonRoomHandler;
import com.github.hypericat.oregoat.feature.features.dungeon.UnitRoom;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.util.StateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.util.Color;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.HashMap;

public class Routes extends Feature implements RenderLastEvent, WorldLoadEvent, ClientTickEvent, RenderChatEvent {

    HashMap<Long, UnitRoom> unitRooms;
    private UnitRoom currentRoom;

    private final Frustum frustum;

    public Routes() {
        this.frustum = new Frustum();
        this.unitRooms = new HashMap<>();
    }

    @Override
    protected void onEnable() {
        EventHandler.register(RenderLastEvent.class, this);
        EventHandler.register(WorldLoadEvent.class, this);
        EventHandler.register(ClientTickEvent.class, this);
        EventHandler.register(RenderChatEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
        EventHandler.unregister(WorldLoadEvent.class, this);
        EventHandler.unregister(ClientTickEvent.class, this);
        EventHandler.unregister(RenderChatEvent.class, this);
    }

    @Override
    public String getName() {
        return "Route";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"Dungeon Route", "Secrets"};
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
        unitRooms.clear();
        this.currentRoom = null;

        for (int x = 0; x <= 10; x++) {
            for (int z = 0; z <= 10; z++) {
                int xPos = UnitRoom.startX + x * UnitRoom.roomSize;
                int zPos = UnitRoom.startZ + z * UnitRoom.roomSize;

                unitRooms.put(encodeIndex(x, z), new UnitRoom(new BlockPos(xPos, 70, zPos)));
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
        return new Point(Math.round((pos.getX() - UnitRoom.startX) / (float) UnitRoom.roomSize), Math.round((pos.getZ() - UnitRoom.startZ) / (float) UnitRoom.roomSize)); // Doesnt work, maybe because it always rounds down?
    }

    public UnitRoom getRoomFromPos(BlockPos pos) {
        return unitRooms.get(encodeIndex(coordToIndexPoint(pos)));
    }

    @Override
    public void onClientTick() {
        if (StateUtil.getLocation() != Location.Dungeon) return;

        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || Minecraft.getMinecraft().theWorld == null) return;

        currentRoom = getRoomFromPos(player.getPosition());

        if (currentRoom == null) return;

        DungeonRoomHandler.initDungeonRoomType(currentRoom);


        //BlockPos local = currentRoom.toLocalPosition(player.getPosition());
        //if (local == null) return;

        //Util.chat("At room pos : x: " + local.getX() + " y: " + local.getY() + " z: " + local.getZ());

    }

    @Override
    public void onRenderChat(ScaledResolution res, RenderGameOverlayEvent.ElementType type, float partialTicks) {
        if (currentRoom != null && currentRoom.hasRoomData()) {
            RenderUtil.renderCenteredText(res.getScaledWidth() >> 1, res.getScaledHeight() >> 1, 0xFFFFFFFF, "Current Room : " + currentRoom.getRoomData().getName());
        }
    }
}
