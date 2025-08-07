package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.feature.features.dungeon.StarredMob;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.util.StateUtil;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.*;

public class DiamanteWarning extends Feature implements PostReceivePacketEvent, ClientTickEvent {

    public DiamanteWarning() {

    }

    private int tick = 0;

    @Override
    protected void onEnable() {
        EventHandler.register(PostReceivePacketEvent.class, this);
        EventHandler.register(ClientTickEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(PostReceivePacketEvent.class, this);
        EventHandler.unregister(ClientTickEvent.class, this);
    }

    @Override
    public String getName() {
        return "Diamante Warning";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    public boolean isValidEntity(Entity entity) {
        if (entity.getClass() != EntityGiantZombie.class) return false;
        if (((EntityGiantZombie) entity).getCurrentArmor(0) == null) return false;
        return ((EntityGiantZombie) entity).getCurrentArmor(0).getItem() == Items.diamond_boots;
    }

    @Override
    public void onPostReceivePacket(Packet<?> packet) {
        //if (!StateUtil.isSkyblock() || StateUtil.getLocation() != Location.Dungeon || !OreConfig.diamante) return;
        if (packet instanceof S04PacketEntityEquipment) {
            S04PacketEntityEquipment mobPacket = (S04PacketEntityEquipment) packet;
            if (mobPacket.getItemStack() == null || mobPacket.getItemStack().getItem() != Items.diamond_boots) return;
            testEntity(mobPacket.getEntityID());
            return;
        }
    }

    public void testEntity(int id) {
        Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(id);
        if (entity == null || !isValidEntity(entity)) return;
        Minecraft.getMinecraft().ingameGUI.displayTitle("§bDiamante", null, 0, 40, 0);
        Minecraft.getMinecraft().ingameGUI.displayTitle(null, "", 0, 40, 0);
        Minecraft.getMinecraft().ingameGUI.displayTitle(null, null, 0, 40, 0);
        tick = 20;
    }

    @Override
    public void onClientTick() {
        if (tick-- > 0) {
            Minecraft.getMinecraft().theWorld.playSound(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, "note.pling", 1f, Math.abs(tick / 10f - 1f), false);
        }
    }
}
