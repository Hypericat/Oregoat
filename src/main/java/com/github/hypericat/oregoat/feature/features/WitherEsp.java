package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ClientTickEvent;
import com.github.hypericat.oregoat.event.events.ReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.RenderLastEvent;
import com.github.hypericat.oregoat.event.events.SendPacketEvent;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.util.Location;
import com.github.hypericat.oregoat.util.RenderUtil;
import com.github.hypericat.oregoat.util.StateUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.network.Packet;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.Sys;
import org.lwjgl.util.Color;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

public class WitherEsp extends Feature implements RenderLastEvent, ClientTickEvent {

    private final List<Entity> witherEntities;

    public WitherEsp() {
        this.witherEntities = new ArrayList<>();
    }

    @Override
    protected void onEnable() {
        EventHandler.register(RenderLastEvent.class, this);
        EventHandler.register(ClientTickEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
        EventHandler.unregister(ClientTickEvent.class, this);
    }

    @Override
    public String getName() {
        return "Wither ESP";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"Maxor", "Storm", "Necron", "Goldor", "Esp"};
    }

    @Override
    public void onClientTick() {
        if (StateUtil.getLocation() != Location.Dungeon) return;

       this.witherEntities.clear();

        for (Entity entity : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!isValidEntity(entity)) continue;

            this.witherEntities.add(entity);
        }
    }

    public boolean isValidEntity(Entity entity) {
        if (!EntityWither.class.isAssignableFrom(entity.getClass())) return false;

        String name = entity.getCustomNameTag().trim();
        switch (name) {
            case "§c§lMaxor": return true;
            case "§c§lGoldor": return true;
            case "§c§lStorm": return true;
            case "§c§lNecron": return true;
            default: return false;
        }
    }

    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (StateUtil.getLocation() != Location.Dungeon) return;

        this.witherEntities.forEach(wither -> RenderUtil.renderBBOutline(RenderUtil.getPartialEntityBoundingBox(wither, partialTicks), partialTicks, new Color(0, 0, 255)));
    }
}
