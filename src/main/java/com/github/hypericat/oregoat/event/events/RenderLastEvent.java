package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface RenderLastEvent extends Event {
    void onRenderLast(RenderGlobal ctx, float partialTicks);
}
