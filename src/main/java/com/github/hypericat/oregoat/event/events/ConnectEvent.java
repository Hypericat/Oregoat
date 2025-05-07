package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;

public interface ConnectEvent extends Event {
    void onConnect(INetHandlerPlayClient handler, NetworkManager manager, boolean local, String type);
}
