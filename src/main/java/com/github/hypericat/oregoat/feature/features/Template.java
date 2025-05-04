package com.github.hypericat.oregoat.feature.features;

import com.github.hypericat.event.EventHandler;
import com.github.hypericat.event.EventState;
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

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onInit() {
        EventHandler.register(ReceivePacketEvent.class, this);
        EventHandler.register(SendPacketEvent.class, this);
    }
}
