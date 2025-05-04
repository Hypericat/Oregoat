package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.event.EventHandler;
import com.github.hypericat.event.events.ReceivePacketEvent;
import com.github.hypericat.event.events.SendPacketEvent;
import com.github.hypericat.oregoat.feature.Feature;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Template extends Feature implements ReceivePacketEvent, SendPacketEvent {

    @Override
    public void onReceivePacket(Packet<?> packet, CallbackInfo ci) {
        System.out.println("Received packet : " + packet.getClass());
    }

    @Override
    public void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        System.out.println("Sent packet : " + packet.getClass());
    }


    @Override
    protected void onEnable() {
        EventHandler.register(ReceivePacketEvent.class, this);
        EventHandler.register(SendPacketEvent.class, this);
    }

    @Override
    protected void onDisable() {
        EventHandler.unregister(ReceivePacketEvent.class, this);
        EventHandler.unregister(SendPacketEvent.class, this);
    }

    @Override
    public String getName() {
        return "Template";
    }

    @Override
    public String[] getAliases() {
        return new String[] {"Test", "Testing"};
    }
}
