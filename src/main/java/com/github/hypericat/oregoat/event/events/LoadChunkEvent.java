package com.github.hypericat.oregoat.event.events;

import com.github.hypericat.oregoat.event.Event;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface LoadChunkEvent extends Event {
    void onLoadChunk(Chunk chunk);
}
