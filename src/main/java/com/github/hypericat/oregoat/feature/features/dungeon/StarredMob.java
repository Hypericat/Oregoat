package com.github.hypericat.oregoat.feature.features.dungeon;

import net.minecraft.client.Minecraft;

public class StarredMob {
    int armorStandID;
    int entityID;

    public StarredMob(Integer armorStandID, Integer entityID) {
        this.armorStandID = armorStandID;
        this.entityID = entityID;
    }

    public boolean isNull() {
        if (Minecraft.getMinecraft().theWorld == null) return true;
        if (Minecraft.getMinecraft().theWorld.getEntityByID(armorStandID) == null || Minecraft.getMinecraft().theWorld.getEntityByID(entityID) == null) return true;
        return false;
    }

    public int getArmorStandID() {
        return armorStandID;
    }

    public int getEntityID() {
        return entityID;
    }
}
