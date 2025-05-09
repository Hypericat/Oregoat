package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.RenderChatEvent;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class GuiNewChatMixin {

    @Inject(method = "drawChat", at = @At("HEAD"), cancellable = true)
    private void onDrawChat(int updateCounter, CallbackInfo ci) {
        //EventHandler.updateListeners(RenderChatEvent.class, e -> ((RenderChatEvent) e).onRenderChat(updateCounter, ci));
    }

}
