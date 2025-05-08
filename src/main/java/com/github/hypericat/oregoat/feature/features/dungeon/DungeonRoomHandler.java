package com.github.hypericat.oregoat.feature.features.dungeon;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DungeonRoomHandler {
    private static final HashMap<Integer, RoomData> rooms = new HashMap<>();

    private DungeonRoomHandler() {

    }

    public static void initDungeonRoomType(UnitRoom room) {

    }

    public static void initCores() {
        Gson gson = new Gson();
        try {
            InputStreamReader reader = new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("oregoat", "rooms.json")).getInputStream());
            JsonArray jsonArray  = gson.fromJson(reader, JsonArray.class);

            jsonArray.forEach(json -> {
                RoomData data = gson.fromJson(json, RoomData.class);
                data.getCores().forEach(core -> rooms.put(core, data));
                System.out.println(data);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
