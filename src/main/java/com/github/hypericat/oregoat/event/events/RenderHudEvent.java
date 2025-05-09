package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public interface RenderHudEvent extends Event {
    void onRenderHud(RenderGameOverlayEvent.ElementType type, ScaledResolution res, float partialTicks);
}
