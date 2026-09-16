package net.slimevoid.littleblocks.compat;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.slimevoid.littleblocks.core.LittleBlocks;
import net.slimevoid.littleblocks.tileentities.TileEntityLittleChunk;

public final class LittleChunkWatchMap {

    private static final Map<Integer, Map<Long, Set<EntityPlayerMP>>> watchedChunksByDimension = new HashMap<>();

    public static void watchChunk(EntityPlayerMP player, ChunkCoordIntPair chunk) {
        var dimension = watchedChunksByDimension.computeIfAbsent(player.dimension, ignored -> new HashMap<>());
        long chunkKey = ChunkCoordIntPair.chunkXZ2Int(chunk.chunkXPos, chunk.chunkZPos);
        var players = dimension.computeIfAbsent(chunkKey, ignored -> new HashSet<>());
        players.add(player);
    }

    public static void unWatchChunk(EntityPlayerMP player, ChunkCoordIntPair chunk) {
        var watchedChunks = watchedChunksByDimension.get(player.dimension);
        if (watchedChunks == null) {
            return;
        }

        long chunkKey = ChunkCoordIntPair.chunkXZ2Int(chunk.chunkXPos, chunk.chunkZPos);
        Set<EntityPlayerMP> players = watchedChunks.get(chunkKey);
        if (players == null) {
            return;
        }

        players.remove(player);
        if (players.isEmpty()) {
            watchedChunks.remove(chunkKey);
        }
        if (watchedChunks.isEmpty()) {
            watchedChunksByDimension.remove(player.dimension);
        }
    }

    public static void onLittleChunkAdded(World world, int x, int z) {
        if (!shouldHandle(world)) {
            return;
        }
        for (EntityPlayerMP player : getWatchingPlayers(world, x >> 4, z >> 4)) {
            LittleBlocks.forgeMultipartCompat.blockWatch(player, x, z);
        }
    }

    public static void onLittleChunkRemoved(World world, int x, int z) {
        if (!shouldHandle(world) || hasLittleChunkInMultipartChunk(world, x, z)) {
            return;
        }
        for (EntityPlayerMP player : getWatchingPlayers(world, x >> 4, z >> 4)) {
            LittleBlocks.forgeMultipartCompat.blockUnWatch(player, x, z);
        }
    }

    public static void onWorldUnload(World world) {
        watchedChunksByDimension.remove(world.provider.dimensionId);
    }

    public static void onPlayerLogout(EntityPlayerMP player) {
        Map<Long, Set<EntityPlayerMP>> watchedChunks = watchedChunksByDimension.get(player.dimension);
        if (watchedChunks == null) {
            return;
        }

        var iterator = watchedChunks.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            var players = iterator.next()
                .getValue();
            players.remove(player);
            if (players.isEmpty()) {
                iterator.remove();
            }
        }
        if (watchedChunks.isEmpty()) {
            watchedChunksByDimension.remove(player.dimension);
        }
    }

    private static boolean shouldHandle(World world) {
        return world != null && !world.isRemote && LittleBlocks.forgeMultipartCompat != null;
    }

    private static Iterable<EntityPlayerMP> getWatchingPlayers(World world, int chunkX, int chunkZ) {
        var watchedChunks = watchedChunksByDimension.get(world.provider.dimensionId);
        if (watchedChunks == null) {
            return Collections.emptyList();
        }

        var players = watchedChunks.get(ChunkCoordIntPair.chunkXZ2Int(chunkX, chunkZ));
        return players == null ? Collections.<EntityPlayerMP>emptyList() : players;
    }

    private static boolean hasLittleChunkInMultipartChunk(World world, int x, int z) {
        Chunk parentChunk = world.getChunkFromBlockCoords(x, z);
        long multipartChunkKey = ForgeMultipartCompat.getMultipartChunkKey(x, z);

        for (TileEntity tile : parentChunk.chunkTileEntityMap.values()) {
            if (tile instanceof TileEntityLittleChunk
                && ForgeMultipartCompat.getMultipartChunkKey(tile.xCoord, tile.zCoord) == multipartChunkKey) {
                return true;
            }
        }
        return false;
    }
}
