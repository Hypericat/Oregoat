package com.github.hypericat.oregoat.event;

import com.github.hypericat.oregoat.event.events.ClientTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ForgeEvents {
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        EventHandler.updateListeners(ClientTickEvent.class, e -> ((ClientTickEvent) e).onClientTick());
    }
}
