package com.github.hypericat.oregoat.util;

import com.github.hypericat.oregoat.gui.screens.OreConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import java.util.List;

public class RenderUtil {

    private RenderUtil() {

    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB bb, Color c, float alphaMultiplier) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = (int) (255 * alphaMultiplier);

        GL11.glLineWidth(OreConfig.outlineThickness);
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GL11.glLineWidth(1);
    }

    public static Vector3d getBBCenter(AxisAlignedBB bb) {
        Vector3d vec = new Vector3d();
        vec.x = (bb.minX + bb.maxX) / 2d;
        vec.y = (bb.minY + bb.maxY) / 2d;
        vec.z = (bb.minZ + bb.maxZ) / 2d;
        return vec;
    }

    public static void drawTracer(Vector3d pos, float eyeHeight, Color c, float alphaMultiplier) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        int a = (int) (255 * alphaMultiplier);

        GL11.glLineWidth(OreConfig.outlineThickness);
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(pos.x, pos.y, pos.z).color(r, g, b, a).endVertex();
        worldrenderer.pos(0, eyeHeight, 0).color(r, g, b, a).endVertex();
        tessellator.draw();


        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GL11.glLineWidth(1);
    }

    public static void renderBlockOutline(BlockPos pos, float partialTicks, Color color) {
        renderBlockOutline(new AxisAlignedBB(pos, pos.add(1, 1, 1)), partialTicks, color);
    }


    public static void renderBlockOutline(AxisAlignedBB bb, float partialTicks, Color color) {
        Entity player = Minecraft.getMinecraft().getRenderViewEntity();

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        bb = bb.offset(-playerX, -playerY, -playerZ);

        RenderUtil.drawOutlinedBoundingBox(bb, color, 1f);
    }

    public static void renderBlockOutlineTracer(BlockPos pos, float partialTicks, Color color) {
        renderBlockOutlineTracer(new AxisAlignedBB(pos, pos.add(1, 1, 1)), partialTicks, color);
    }

    public static void renderBlockOutlineTracer(AxisAlignedBB bb, float partialTicks, Color color) {
        Entity player = Minecraft.getMinecraft().getRenderViewEntity();
        float eyeHeight = Minecraft.getMinecraft().thePlayer.getEyeHeight();

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        bb = bb.offset(-playerX, -playerY, -playerZ);

        RenderUtil.drawOutlinedBoundingBox(bb, color, 1f);
        RenderUtil.drawTracer(RenderUtil.getBBCenter(bb), eyeHeight, new Color(0, 255, 0), 1f);
    }

    public static void renderCenteredText(int x, int y, int color, String... text) {

        if (text.length == 0) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, 0f);

        int fontHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1;
        float yTextOffset = -0.5f * text.length * fontHeight;

        for (int i = 0; i < text.length; i++) {
            String line = text[i];
            float textWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(line) / -2f;
            Minecraft.getMinecraft().fontRendererObj.drawString(line, textWidth, yTextOffset + i * fontHeight, color, false);
        }

 
        GlStateManager.popMatrix();
    }


    /**
     * Taken from NotEnoughUpdates under Creative Commons Attribution-NonCommercial 3.0
     * https://github.com/Moulberry/NotEnoughUpdates/blob/master/LICENSE
     * @author Moulberry
     */
    public static void drawFilledBoundingBox(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.color(c.getRed()/255f, c.getGreen()/255f, c.getBlue()/255f, c.getAlpha()/255f*alphaMultiplier);

        //vertical
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        tessellator.draw();


        GlStateManager.color(c.getRed()/255f*0.8f, c.getGreen()/255f*0.8f, c.getBlue()/255f*0.8f, c.getAlpha()/255f*alphaMultiplier);

        //x
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        tessellator.draw();


        GlStateManager.color(c.getRed()/255f*0.9f, c.getGreen()/255f*0.9f, c.getBlue()/255f*0.9f, c.getAlpha()/255f*alphaMultiplier);
        //z
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldrenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
        worldrenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
