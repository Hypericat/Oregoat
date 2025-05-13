package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.RenderBlockSolidEvent;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public abstract class ChunkMixin {


    @Shadow protected abstract Block getBlock0(int x, int y, int z);

    @Shadow @Final public int xPosition;

    @Shadow @Final public int zPosition;

    @Shadow public abstract IBlockState getBlockState(BlockPos pos);

    @Inject(method = "fillChunk", at = @At("TAIL"), cancellable = false)
    private void onFillChunk(byte[] p_177439_1_, int p_177439_2_, boolean p_177439_3_, CallbackInfo ci) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    if (this.getBlock0(x, y, z) == Blocks.stained_hardened_clay) {
                        Block block = this.getBlock0(x, y, z);
                        if (this.getBlockState(new BlockPos(x, y, z)).getValue(BlockColored.COLOR).getMetadata() != EnumDyeColor.ORANGE.getMetadata()) continue;

                        Util.chat("Found Golden Dragon at position : " + (this.xPosition * 16) + ", " + y + ", " + this.zPosition * 16);
                        return;
                    }
                }
            }
        }
    }

}
