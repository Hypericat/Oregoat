package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.entity.Entity;

public interface EntitySpawnEvent extends Event {
    void onEntitySpawn(Entity entity);
}
