package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface PostReceivePacketEvent extends Event {
    void onPostReceivePacket(Packet<?> packet, CallbackInfo ci);
}
