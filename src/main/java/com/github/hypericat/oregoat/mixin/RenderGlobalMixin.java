package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.RenderBlockSolidEvent;
import com.github.hypericat.oregoat.mixinInterface.IGlobalRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin implements IGlobalRenderer {

    @Shadow
    public static void drawOutlinedBoundingBox(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha) {
    }

    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;DILnet/minecraft/entity/Entity;)I", at = @At("HEAD"), cancellable = false)
    private void onChannelRead(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn, CallbackInfoReturnable<Integer> cir) {
        EventHandler.updateListeners(RenderBlockSolidEvent.class, event -> ((RenderBlockSolidEvent) event).onRenderBlockSolid(blockLayerIn, partialTicks, pass, entityIn, cir));
    }

    @Override
    public void drawBoundingBox(AxisAlignedBB boundingBox, int red, int green, int blue, int alpha) {
        drawOutlinedBoundingBox(boundingBox, red, green, blue, alpha);
    }
}
