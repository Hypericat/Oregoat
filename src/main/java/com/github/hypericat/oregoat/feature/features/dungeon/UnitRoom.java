package com.github.hypericat.oregoat.feature.features.dungeon;

import com.github.hypericat.oregoat.util.Box;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

import java.awt.*;

public class UnitRoom {
    private final Box box;

    public UnitRoom(BlockPos center) {
        this.box = new Box(new Point(center.getX(), center.getZ()), 30 ,30);
    }

    public Box getBox() {
        return this.box;
    }

    // May and will return null be careful
    public BlockPos toLocalPosition(BlockPos absolutePos) {
        if (!box.to3d(0, 256).isVecInside(new Vec3(absolutePos))) return null;
        return absolutePos.subtract(new Vec3i(box.getMin().x, 0, box.getMin().y));
    }

    public BlockPos toAbsolutePosition(BlockPos localPos) {
        return localPos.add(new Vec3i(box.getMin().x, 0, box.getMin().y));
    }

}
