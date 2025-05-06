package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.oregoat.RenderUtil;
import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.ReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.RenderBlockSolidEvent;
import com.github.hypericat.oregoat.event.events.RenderLastEvent;
import com.github.hypericat.oregoat.event.events.SendPacketEvent;
import com.github.hypericat.oregoat.feature.Feature;
import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.mixin.RenderGlobalMixin;
import com.github.hypericat.oregoat.mixinInterface.IGlobalRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.*;
import org.apache.logging.log4j.core.appender.routing.Route;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class Routes extends Feature implements RenderLastEvent {

    private List<BlockPos> blocks;

    private Frustum frustum;

    public Routes() {
        this.frustum = new Frustum();
        this.blocks = new ArrayList<>();
        blocks.add(new BlockPos(0, 0, 0));
    }

    @Override
    protected void onEnable() {
        EventHandler.register(RenderLastEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(RenderLastEvent.class, this);
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
        float eyeHeight = Minecraft.getMinecraft().thePlayer.getEyeHeight();
        BlockPos pos = new BlockPos(0, 72, 0);

        AxisAlignedBB bb = new AxisAlignedBB(pos, pos.add(1, 1, 1));

        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        frustum.setPosition(player.posX, player.posY, player.posZ);
        if (!frustum.isBoundingBoxInFrustum(bb)) return; // Dont return for tracer, also fix tracer with viewbobbing

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        bb = bb.offset(-playerX, -playerY, -playerZ);

        // Set line width here

        RenderUtil.drawOutlinedBoundingBox(bb, new Color(0, 255, 0), 1f);
        RenderUtil.drawTracer(RenderUtil.getBBCenter(bb), eyeHeight, new Color(0, 255, 0), 1f);
        //RenderUtil.drawFilledBoundingBox(bb, new Color(0, 0, 255), 0.8f);
    }

}
