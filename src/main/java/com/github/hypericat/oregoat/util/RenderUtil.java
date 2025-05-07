package com.github.hypericat.oregoat.util;

import com.github.hypericat.oregoat.gui.screens.OreConfig;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vector3d;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

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
