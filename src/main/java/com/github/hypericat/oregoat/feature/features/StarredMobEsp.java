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
import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.Sys;

import java.util.*;
import java.util.stream.Collectors;

public class StarredMobEsp extends Feature implements RenderLastEvent, ClientTickEvent, WorldUnloadEvent, PostReceivePacketEvent {

    private final HashMap<Integer, StarredMob> starredMobs;
    private final HashSet<Integer> starredStands;

    private static final int saHash = "Shadow Assassin".hashCode();

    public StarredMobEsp() {
        this.starredMobs = new HashMap<>();
        this.starredStands = new HashSet<>();
    }

    @Override
    protected void onEnable() {
        EventHandler.register(ClientTickEvent.class, this);
        EventHandler.register(RenderLastEvent.class, this);
        EventHandler.register(PostReceivePacketEvent.class, this);
        EventHandler.register(WorldUnloadEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
        EventHandler.unregister(ClientTickEvent.class, this);
        EventHandler.unregister(PostReceivePacketEvent.class, this);
        EventHandler.unregister(WorldUnloadEvent.class, this);
    }

    @Override
    public String getName() {
        return "Starred Mob ESP";
    }

    @Override
    public String[] getAliases() {
        return new String[] {};
    }

    public boolean isValidEntity(Entity entity) {
        if (entity.getClass() != EntityArmorStand.class) return false;
        if (!entity.hasCustomName() || !entity.getCustomNameTag().contains("§6✯ ") || !entity.getCustomNameTag().endsWith("§c❤") || !entity.getAlwaysRenderNameTag()) return false;
        return true;
    }

    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.starredMobEsp) return;

        //Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
        //if (entity != null) {
        //    AxisAlignedBB bb = RenderUtil.getPartialEntityBoundingBox(entity, partialTicks);
        //    RenderUtil.renderBBOutline(bb, partialTicks, OreConfig.witherESPColor.toJavaColor());
        //    Util.chat(entity.getName());
        //    Util.chat(entity.getClass().toString());
        //}

        // Frustum culling ////////////////////
        List<Integer> entityIDS = new ArrayList<>(this.starredMobs.keySet());
        for (int entityID : entityIDS) {
            Entity e = Minecraft.getMinecraft().theWorld.getEntityByID(entityID);
            if (e == null) {
                this.starredMobs.remove(entityID);
                continue;
            }

            AxisAlignedBB boundingBox = RenderUtil.getPartialEntityBoundingBox(e, partialTicks);
            RenderUtil.renderBBOutline(boundingBox, partialTicks, OreConfig.starredMobEspColor.toJavaColor());
        }
    }

    public void removeStarredMob(int id) {
        if (starredMobs.containsKey(id)) {
            int armor = starredMobs.get(id).getArmorStandID();
            starredMobs.remove(id);
            if (armor != -1)
                starredStands.remove(armor);
        }
    }


    public void onPostReceivePacket(Packet<?> packet) {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.starredMobEsp) return;

        if (packet.getClass() == S13PacketDestroyEntities.class) {
            Arrays.stream(((S13PacketDestroyEntities) packet).getEntityIDs()).forEach(this::removeStarredMob);
        }
    }

    @Override
    public void onClientTick() {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.starredMobEsp) return;

        for (EntityArmorStand armorStand : Minecraft.getMinecraft().theWorld.getEntities(EntityArmorStand.class, Entity::hasCustomName)) {
            if (armorStand == null || starredStands.contains(armorStand.getEntityId())) continue;
            if (!isValidEntity(armorStand)) continue;

            addStarredEntity(armorStand.getEntityId(), Util.getMobEntity(armorStand));
        }

        // Check for shadow assassins
        for (Entity player : Minecraft.getMinecraft().theWorld.getPlayers(Entity.class, entity -> entity.getName().hashCode() == saHash)) {
            if (starredMobs.containsKey(player.getEntityId())) continue;
            addStarredEntity(-1, player);
        }
    }

    public void addStarredEntity(int armorStand, Entity mobEntity) {
        if (mobEntity == null) {
            //System.err.println("Unable to find mob for entity : " + armorStand.getName());
            return;
        }

        if (armorStand != -1) {
            starredStands.add(armorStand);
        }
        starredMobs.put(mobEntity.getEntityId(), new StarredMob(armorStand, mobEntity.getEntityId()));
    }

    @Override
    public void onWorldUnload(World world) {
        starredStands.clear();
        starredMobs.clear();
    }
}
