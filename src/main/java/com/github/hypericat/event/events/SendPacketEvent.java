package com.github.hypericat.event.events;

import com.github.hypericat.event.Event;
import com.github.hypericat.event.EventState;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface SendPacketEvent extends Event {
    void onSendPacket(Packet<?> packet, CallbackInfo ci);
}
