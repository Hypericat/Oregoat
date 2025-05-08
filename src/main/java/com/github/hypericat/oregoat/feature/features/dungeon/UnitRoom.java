package com.github.hypericat.oregoat.feature.features.dungeon;

import com.github.hypericat.oregoat.util.Box;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.chunk.Chunk;

import java.awt.*;

public class UnitRoom {
    public static final int roomSize = 32;
    /**
     * The starting coordinates to start scanning (the north-west corner).
     */
    public static final int startX = -185;
    public static final int startZ = -185;
    private final BlockPos center;


    private final Box box;

    public UnitRoom(BlockPos center) {
        this.box = new Box(new Point(center.getX(), center.getZ()), 15 ,15);
        this.center = center;
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


    // Stolen from Funny Maps
    public int getCore() {
        StringBuilder stringBuilder = new StringBuilder(150);

        // Get the chunk at the given coordinates
        Chunk chunk = Minecraft.getMinecraft().theWorld.getChunkFromChunkCoords(center.getX() >> 4, center.getZ()>> 4);

        // Get the height at local chunk coordinates (0-15)
        int height = chunk.getHeightValue(center.getX() & 15, center.getZ() & 15);
        height = Math.max(11, Math.min(height, 140)); // Clamp height to [11, 140]

        // Append initial '0's
        for (int i = 0; i < 140 - height; i++) {
            stringBuilder.append('0');
        }

        int bedrock = 0;

        for (int y = height; y >= 12; y--) {
            BlockPos pos = new BlockPos(center.getX(), y, center.getZ());
            Block block = chunk.getBlock(pos); // May need to use world.getBlockState(pos).getBlock() in newer versions
            int id = Block.getIdFromBlock(block);

            if (id == 0 && bedrock >= 2 && y < 69) {
                for (int i = 0; i < y - 11; i++) {
                    stringBuilder.append('0');
                }
                break;
            }

            if (id == 7) {
                bedrock++;
            } else {
                bedrock = 0;

                if (id == 5 || id == 54 || id == 146) {
                    continue;
                }
            }

            stringBuilder.append(id);
        }

        return stringBuilder.toString().hashCode();
    }



}
