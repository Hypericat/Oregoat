package com.github.hypericat.oregoat.feature.features.dungeon;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.HashMap;

public class DungeonRoomHandler {
    HashMap<Integer, RoomData> rooms;

    private DungeonRoomHandler() {

    }

    public static void initDungeonRoomType(UnitRoom room) {

    }

    public static void initCores() {
        Gson gson = new Gson();
        try {
            //gson.fromJson()
            Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("oregoat", "room.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
