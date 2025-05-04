package com.github.hypericat.event.events;

import com.github.hypericat.event.Event;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface ReceivePacketEvent extends Event {
    void onReceivePacket(Packet<?> packet, CallbackInfo ci);
}
