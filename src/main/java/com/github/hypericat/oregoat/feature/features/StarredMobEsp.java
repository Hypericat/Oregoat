package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ClientTickEvent;
import com.github.hypericat.oregoat.event.events.PostReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.ReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.RenderLastEvent;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.util.StateUtil;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.regex.Pattern;

public class StarredMobEsp extends Feature implements RenderLastEvent, PostReceivePacketEvent {

    private final HashMap<Integer, Entity> starredMobs;
    private final Pattern pattern;

    public StarredMobEsp() {
        this.starredMobs = new HashMap<>();
        this.pattern = Pattern.compile("/^§6✯ (?:§.)*(.+)§r.+§c❤$|^(Shadow Assassin)$/");
    }

    @Override
    protected void onEnable() {
        EventHandler.register(PostReceivePacketEvent.class, this);
        EventHandler.register(RenderLastEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
        EventHandler.unregister(PostReceivePacketEvent.class, this);
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
        if (!entity.hasCustomName() || !entity.getCustomNameTag().startsWith("§6✯ ") || !entity.getCustomNameTag().endsWith("§c❤") || !entity.getAlwaysRenderNameTag()) return false;
        return true;
    }

    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.starredMobEsp) return;

        Entity entity = Minecraft.getMinecraft().objectMouseOver.entityHit;
        if (entity == null) return;
        AxisAlignedBB boundingBox = RenderUtil.getPartialEntityBoundingBox(entity, partialTicks);
        RenderUtil.renderBBOutline(boundingBox, partialTicks, OreConfig.starredMobEspColor.toJavaColor());
        System.out.println(entity.getName());

        //this.starredMobs.values().forEach(entity -> {
        //    AxisAlignedBB boundingBox = RenderUtil.getPartialEntityBoundingBox(entity, partialTicks);
        //    RenderUtil.renderBBOutline(boundingBox, partialTicks, OreConfig.starredMobEspColor.toJavaColor());
        //});
    }


    @Override
    public void onPostReceivePacket(Packet<?> packet, CallbackInfo ci) {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.starredMobEsp) return;

        if (packet.getClass() == S1CPacketEntityMetadata.class) {
            S1CPacketEntityMetadata meta = (S1CPacketEntityMetadata) packet;
            Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(meta.getEntityId());
            if (entity == null) return;
            if (isValidEntity(entity)) {
                System.out.println("Found starred mob : " + entity.getName());
                starredMobs.put(entity.getEntityId(), entity);
            }
        }
    }
}
