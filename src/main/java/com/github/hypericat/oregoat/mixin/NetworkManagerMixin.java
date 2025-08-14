package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.PostReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.ReceivePacketEvent;
import com.github.hypericat.oregoat.event.events.SendPacketEvent;
import com.github.hypericat.oregoat.util.Util;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
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

    @Inject(method = "channelRead0", at = @At("RETURN"))
    private void onChannelReadPost(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
        EventHandler.updateListeners(PostReceivePacketEvent.class, event -> ((PostReceivePacketEvent) event).onPostReceivePacket(packet));
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        EventHandler.updateListeners(SendPacketEvent.class, event -> ((SendPacketEvent) event).onSendPacket(packet, ci));
    }

}
