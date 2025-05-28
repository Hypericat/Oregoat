package com.github.hypericat.oregoat.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class Util {
    public static void chat(@NotNull String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message.replaceAll("&&", "§")));
    }

    public static void chatOfficial(@NotNull String message) {
        chat("§4[Oregoat]§9 " + message);
    }

    public static String blockPosToString(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
    }

    public static float getSBMaxHealth(EntityLivingBase entity) {
        if (entity == null) return -1f;

        IAttributeInstance attribute = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        if (attribute == null) return -1f;
        return (float) attribute.getBaseValue();
    }

    public static Entity getMobEntity(EntityArmorStand armorStand) {
        if (Minecraft.getMinecraft().theWorld == null || armorStand == null) return null;
        return Minecraft.getMinecraft().theWorld.getEntitiesWithinAABBExcludingEntity(armorStand, armorStand.getEntityBoundingBox().offset(0.0, -1.0, 0.0))
                .stream()
                .filter(e -> e.getClass() != EntityArmorStand.class && e != Minecraft.getMinecraft().thePlayer && !(e.getClass() == EntityWither.class && e.isInvisible()) && e.getClass() != EntityOtherPlayerMP.class)
                .min(Comparator.comparingDouble(e -> e.getDistanceToEntity(armorStand))).orElse(null);
    }
}
