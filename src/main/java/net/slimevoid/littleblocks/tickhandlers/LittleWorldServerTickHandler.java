package net.slimevoid.littleblocks.tickhandlers;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.slimevoid.littleblocks.compat.LittleChunkWatchMap;
import net.slimevoid.littleblocks.core.LittleBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public class LittleWorldServerTickHandler {

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && LittleBlocks.forgeMultipartCompat != null) {
            LittleBlocks.forgeMultipartCompat.runEndTick();
        }
    }

    @SubscribeEvent
    public void chunkWatch(ChunkWatchEvent.Watch watch) {
        if (watch.player instanceof FakePlayer) {
            return;
        }
        LittleChunkWatchMap.watchChunk(watch.player, watch.chunk);
        if (LittleBlocks.forgeMultipartCompat != null) {
            Chunk chunk = watch.player.worldObj.getChunkFromChunkCoords(watch.chunk.chunkXPos, watch.chunk.chunkZPos);
            LittleBlocks.forgeMultipartCompat.chunkWatch(watch.player, chunk);
        }
    }

    @SubscribeEvent
    public void chunkUnWatch(ChunkWatchEvent.UnWatch watch) {
        if (watch.player instanceof FakePlayer) {
            return;
        }
        LittleChunkWatchMap.unWatchChunk(watch.player, watch.chunk);
        if (LittleBlocks.forgeMultipartCompat != null) {
            Chunk chunk = watch.player.worldObj.getChunkFromChunkCoords(watch.chunk.chunkXPos, watch.chunk.chunkZPos);
            LittleBlocks.forgeMultipartCompat.chunkUnWatch(watch.player, chunk);
        }
    }
}
