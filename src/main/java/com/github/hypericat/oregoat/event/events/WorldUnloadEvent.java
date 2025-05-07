package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.world.World;

public interface WorldUnloadEvent extends Event {
    void onWorldUnload(World world);
}
