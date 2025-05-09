package com.github.hypericat.oregoat.event;

import com.github.hypericat.oregoat.event.events.*;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ForgeEvents {
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        EventHandler.updateListeners(ClientTickEvent.class, e -> ((ClientTickEvent) e).onClientTick());
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        EventHandler.updateListeners(RenderLastEvent.class, e -> ((RenderLastEvent) e).onRenderLast(event.context, event.partialTicks));
    }

    @SubscribeEvent
    public void onRenderHud(RenderGameOverlayEvent event) {
        EventHandler.updateListeners(RenderHudEvent.class, e -> ((RenderHudEvent) e).onRenderHud(event.type, event.resolution, event.partialTicks));
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        EventHandler.updateListeners(WorldLoadEvent.class, e -> ((WorldLoadEvent) e).onWorldLoad(event.world));
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        EventHandler.updateListeners(WorldUnloadEvent.class, e -> ((WorldUnloadEvent) e).onWorldUnload(event.world));
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        EventHandler.updateListeners(DisconnectEvent.class, e -> ((DisconnectEvent) e).onDisconnect(event.handler, event.manager));
    }

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        EventHandler.updateListeners(ConnectEvent.class, e -> ((ConnectEvent) e).onConnect(event.handler, event.manager, event.isLocal, event.connectionType));
    }
}
