package com.github.hypericat.oregoat.mixinInterface;

import net.minecraft.util.AxisAlignedBB;

public interface IGlobalRenderer {
    public void drawBoundingBox(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha);
}
