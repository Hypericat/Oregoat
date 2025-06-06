package com.github.hypericat.oregoat.event;

import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EventHandler {
    private final static HashMap<Class<? extends Event>, HashSet<Event>> EVENTS = new HashMap<>();

    private EventHandler() {

    }

    public static <T extends Event> void register(Class<? extends Event> clss, T event) {
        if (!EVENTS.containsKey(clss))
            EVENTS.put(clss, new HashSet<>());
        else
            if (EVENTS.get(clss).contains(event)) return; // Duplicate register
        EVENTS.get(clss).add(event); // It should add to hashet with the hashcode() being the mem address
    }


    // Untested
    public static <T extends Event> void unregister(Class<? extends Event> clss, T event) {
        if (!EVENTS.containsKey(clss)) return;
        EVENTS.get(clss).remove(event);
    }


    // May return null, need to check
    public static Stream<? extends Event> getListeners(Class<?> clss) {
        if (!EVENTS.containsKey(clss)) return null;
        return EVENTS.get(clss).stream();
    }

    public static void updateListeners(Class<?> clss, Consumer<Event> consumer) {
        if (!EVENTS.containsKey(clss)) return;
        EVENTS.get(clss).forEach(consumer);
    }

    public static void registerForgeEvents() {
        MinecraftForge.EVENT_BUS.register(new ForgeEvents());
    }

}
