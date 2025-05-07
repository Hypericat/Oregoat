package com.github.hypericat.oregoat.util;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.awt.*;

public class Box {
    int minX;
    int minY;
    int maxX;
    int maxY;

    public Box(Point point1, Point point2) {
        this(point1.x, point1.y, point2.x, point2.y);
    }

    public Box(int x1, int y1, int x2, int y2) {
        this.maxX = Math.max(x1, x2);
        this.minX = Math.min(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.minY = Math.min(y1, y2);
    }


    // Make sure width and height are both divisible by 2
    public Box(Point center, int width, int height) {
        this(center.x + (width >> 1), center.y + (height >> 1), center.x - (width >> 1), center.y - (height >> 1));
    }

    public Point getMax() {
        return new Point(maxX, maxY);
    }

    public Point getMin() {
        return new Point(minX, minY);
    }

    public AxisAlignedBB to3dCentered(int y, int height) {
        height >>= 1;
        return to3d(y - height, y + height);
    }

    public AxisAlignedBB to3d(int minY, int maxY) {
        return new AxisAlignedBB(this.minX, minY, this.minY, this.maxX, maxY, this.maxY);
    }

    public String toString() {
        return "Centered at : " + ((maxX + minX) >> 1) + ((maxY + minY) >> 1);
    }
}
