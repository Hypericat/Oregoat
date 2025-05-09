package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface RenderChatEvent extends Event {
    void onRenderChat(ScaledResolution res, RenderGameOverlayEvent.ElementType type, float partialTicks);
}
