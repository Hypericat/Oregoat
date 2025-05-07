package com.github.hypericat.oregoat.util;

import com.github.hypericat.oregoat.event.EventHandler;
import com.github.hypericat.oregoat.event.events.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class StateUtil implements WorldLoadEvent, WorldUnloadEvent, DisconnectEvent, ConnectEvent, ReceivePacketEvent, ClientTickEvent {
    private static StateUtil instance;
    private static boolean hypixel = false;
    private static boolean skyblock = false;
    private static Location location = Location.Unknown;

    //Most stolen from https://github.com/Wadey3636/JPA-2/blob/76aa3fd13b9e68e044670c95de1a3e50dc0d03c8/src/main/kotlin/com/github/wadey3636/jpa/utils/location/LocationUtils.kt#L22

    private StateUtil() {
        EventHandler.register(WorldLoadEvent.class, this);
        EventHandler.register(WorldUnloadEvent.class, this);
        EventHandler.register(DisconnectEvent.class, this);
        EventHandler.register(ConnectEvent.class, this);
        EventHandler.register(ReceivePacketEvent.class, this);
        EventHandler.register(ClientTickEvent.class, this);
    }

    public static void init() {
        if (instance != null) return;
        instance = new StateUtil();
    }


    public static boolean isHypixel() {
        return hypixel;
    }

    public static boolean isSkyblock() {
        return skyblock;
    }

    public static Location getLocation() {
        return location;
    }

    @Override
    public void onWorldLoad(World world) {

    }

    @Override
    public void onWorldUnload(World world) {
        skyblock = false;
        location = Location.Unknown;
    }

    @Override
    public void onDisconnect(INetHandlerPlayClient handler, NetworkManager manager) {
        hypixel = false;
        skyblock = false;
        location = Location.Unknown;
    }

    @Override
    public void onConnect(INetHandlerPlayClient handler, NetworkManager manager, boolean local, String type) {
        hypixel = false;
        if (local) return;

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.getClientBrand() != null) {
            if (player.getClientBrand().toLowerCase().contains("hypixel")) {
                hypixel = true;
                return;
            }
        }

        ServerData data = Minecraft.getMinecraft().getCurrentServerData();
        if (data == null || data.serverIP == null) return;

        hypixel = data.serverIP.contains("hypixel");
    }

    @Override
    public void onReceivePacket(Packet<?> packet, CallbackInfo ci) {
        if (hypixel || packet.getClass() != S3FPacketCustomPayload.class || ((S3FPacketCustomPayload) packet).getChannelName() != "MC|Brand") return;
        PacketBuffer buffer = ((S3FPacketCustomPayload) packet).getBufferData();
        if (buffer == null) return;
        String s = buffer.readStringFromBuffer(Short.MAX_VALUE);
        if (s == null) return;
        hypixel = s.contains("hypixel");
    }

    public void updateSkyblock() {
        if (skyblock || !hypixel) return;
        if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().theWorld.getScoreboard() == null || Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1) == null) return;
        skyblock = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName().toLowerCase().contains("skyblock");
        if (skyblock) System.out.println("In skyblock!");
    }

    public void updateArea() {
        if (location != Location.Unknown) return;

        if (Minecraft.getMinecraft().isSingleplayer()) {
            location = Location.SinglePlayer;
            return;
        }

        if (!skyblock || !hypixel) return;
        if (Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().thePlayer.sendQueue == null || Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap() == null) return;
        Collection<NetworkPlayerInfo> info = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
        Optional<NetworkPlayerInfo> op = info.stream().filter(in -> in.getDisplayName() != null && in.getDisplayName().getUnformattedText() != null && (in.getDisplayName().getUnformattedText().startsWith("Area: ") || in.getDisplayName().getUnformattedText().startsWith("Dungeon: "))).findFirst();
        if (!op.isPresent()) return;
        location = Arrays.stream(Location.values()).filter(location -> location.match(op.get().getDisplayName().getUnformattedText())).findFirst().orElse(Location.Unknown);
        System.out.println("Currently in : " + location.getName());
    }

    @Override
    public void onClientTick() { // Add some kind of cooldown if unsuccessful
        updateSkyblock();
        updateArea();
    }
}
