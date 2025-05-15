package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ClientTickEvent;
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
import net.minecraft.util.AxisAlignedBB;

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
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.witherEsp) return;

       this.witherEntities.clear();

        for (Entity entity : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            if (!isValidEntity(entity)) continue;

            this.witherEntities.add(entity);
        }
    }

    public boolean isValidEntity(Entity entity) {
        //if (EntityWither.class.isAssignableFrom(entity.getClass())) {
        //    EntityWither wither = (EntityWither) entity;
        //    System.out.println("Found wither :" + entity.getName() + ": with health " + Util.getSBMaxHealth(wither));
        //}

        if (!EntityWither.class.isAssignableFrom(entity.getClass())) return false;

        //String name = entity.getCustomNameTag();
        //return name.startsWith("§e﴾ §c§lMaxor") || name.startsWith("§e﴾ §c§lNecron") || name.startsWith("§e﴾ §c§lGoldor") || name.startsWith("§e﴾ §c§lStorm");

        return Util.getSBMaxHealth((EntityWither) entity) > 400f; // Default is 300
    }

    @Override
    public void onRenderLast(RenderGlobal ctx, float partialTicks) {
        if (StateUtil.getLocation() != Location.Dungeon || !OreConfig.witherEsp) return;

        this.witherEntities.forEach(entity -> {
            //AxisAlignedBB boundingBox = RenderUtil.getPartialEntityBoundingBox(entity, partialTicks).expand(0.45d, 1.25d, 0.45d).offset(0d, -2.5d, 0d);
            AxisAlignedBB boundingBox = RenderUtil.getPartialEntityBoundingBox(entity, partialTicks);

            if (OreConfig.witherEspTracer)
                RenderUtil.renderBBOutlineTracer(boundingBox, partialTicks, OreConfig.witherESPColor.toJavaColor());
            else
                RenderUtil.renderBBOutline(boundingBox, partialTicks, OreConfig.witherESPColor.toJavaColor());
        });
    }
}
