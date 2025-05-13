package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.LoadChunkEvent;
import com.github.hypericat.oregoat.mixinInterface.IChunk;
import net.minecraft.block.Block;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chunk.class)
public abstract class ChunkMixin implements IChunk {

    @Shadow protected abstract net.minecraft.block.Block getBlock0(int x, int y, int z);

    @Inject(method = "fillChunk", at = @At("TAIL"))
    private void onFillChunk(byte[] p_177439_1_, int p_177439_2_, boolean p_177439_3_, CallbackInfo ci) {
        EventHandler.updateListeners(LoadChunkEvent.class, e -> ((LoadChunkEvent) e).onLoadChunk((Chunk) (Object) this));
    }

    @Override
    public Block getBlockLocal(int x, int y, int z) {
        return this.getBlock0(x, y, z);
    }
}
