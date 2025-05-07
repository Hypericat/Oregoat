package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public interface DisconnectEvent extends Event {
    void onDisconnect(INetHandlerPlayClient handler, NetworkManager manager);
}
