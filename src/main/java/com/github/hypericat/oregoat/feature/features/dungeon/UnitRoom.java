package com.github.hypericat.oregoat.feature.features.dungeon;

import com.github.hypericat.oregoat.util.Box;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.awt.*;

public class UnitRoom {
    private final Box box;

    public UnitRoom(BlockPos center) {
        this.box = new Box(new Point(center.getX(), center.getZ()), 30 ,30);
    }

    public Box getBox() {
        return this.box;
    }
}
