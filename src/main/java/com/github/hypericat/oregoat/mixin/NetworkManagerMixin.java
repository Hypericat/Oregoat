package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.event.EventHandler;
import com.github.hypericat.event.EventState;
import com.github.hypericat.event.events.ReceivePacketEvent;
import com.github.hypericat.event.events.SendPacketEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class NetworkManagerMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        EventHandler.updateListeners(ReceivePacketEvent.class, event -> ((ReceivePacketEvent) event).onReceivePacket(packet, ci));

    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        EventHandler.updateListeners(SendPacketEvent.class, event -> ((SendPacketEvent) event).onSendPacket(packet, ci));
    }

}
