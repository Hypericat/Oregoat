package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface RenderBlockSolidEvent extends Event {
    void onRenderBlockSolid(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn, CallbackInfoReturnable<Integer> cir);
}
