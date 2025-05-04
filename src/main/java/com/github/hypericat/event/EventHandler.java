package com.github.hypericat.event;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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



    public static Stream<? extends Event> getListeners(Class<?> clss) {
        System.out.println(EVENTS.get(clss));
        return EVENTS.get(clss).stream();
    }


}
