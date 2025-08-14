package com.github.hypericat.oregoat.mixin;

import com.github.hypericat.oregoat.gui.screens.OreConfig;
import com.github.hypericat.oregoat.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(PlayerControllerMP.class)
public class GuiContainerMixin {
    @Unique
    private static final List<String> termNames = Arrays.asList(
            "Correct all the panes!",
            "Click in order!",
            "What starts with:",
            "Select all the",
            "Change all to same color!"
    );


    @Inject(method = "windowClick", at = @At("HEAD"), cancellable = true)
    protected void onSlotClick(int windowId, int slotId, int mouseButtonClicked, int mode, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> cir) {
        if (!OreConfig.autotermCancel || !isTerm(Minecraft.getMinecraft().currentScreen)) return;

        Util.chatOfficial("Blocked Term Click!");
        cir.setReturnValue(null);
    }

    @Unique
    private static boolean isTerm(Gui container) {
        if (container == null) return false;
        if (!(container instanceof GuiChest)) return false;

        IInventory inventory = ((GuiChestAccessor) container).getLowerInventory();
        if (inventory == null || inventory.getName() == null) return false;

        return termNames.stream().anyMatch(s -> inventory.getName().startsWith(s));
    }

}
