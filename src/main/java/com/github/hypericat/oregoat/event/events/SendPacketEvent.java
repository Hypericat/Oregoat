package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface SendPacketEvent extends Event {
    void onSendPacket(Packet<?> packet, CallbackInfo ci);
}
