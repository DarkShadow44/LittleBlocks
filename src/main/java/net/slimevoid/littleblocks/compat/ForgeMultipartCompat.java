package net.slimevoid.littleblocks.compat;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.slimevoid.littleblocks.tileentities.TileEntityLittleChunk;
import net.slimevoid.littleblocks.world.LittleFakeEntityPlayer;

import codechicken.multipart.handler.MultipartSPH;
import codechicken.multipart.handler.MultipartSaveLoad;
import scala.collection.JavaConverters;
import scala.collection.Seq;

public final class ForgeMultipartCompat {

    public void loadTiles(World world, Map<ChunkPosition, TileEntity> tiles) {
        MultipartSaveLoad.loadTiles(world, tiles);
    }

    public void runEndTick() {
        Seq<EntityPlayerMP> playersSeq = JavaConverters
            .asScalaIteratorConverter(
                LittleFakeEntityPlayer.getFakePlayers()
                    .iterator())
            .asScala()
            .toSeq();
        MultipartSPH.onTickEnd(playersSeq);
    }

    public void blockWatch(EntityPlayerMP player, int x, int z) {
        EntityPlayerMP fakePlayer = LittleFakeEntityPlayer.getFakePlayer(player);
        MultipartSPH.onChunkWatch(fakePlayer, getMultipartChunkCoords(x, z));
    }

    public void blockUnWatch(EntityPlayerMP player, int x, int z) {
        EntityPlayerMP fakePlayer = LittleFakeEntityPlayer.getFakePlayer(player);
        MultipartSPH.onChunkUnWatch(fakePlayer, getMultipartChunkCoords(x, z));
    }

    public void chunkWatch(EntityPlayerMP player, Chunk chunk) {
        for (TileEntity tile : chunk.chunkTileEntityMap.values()) {
            if (tile instanceof TileEntityLittleChunk) {
                blockWatch(player, tile.xCoord, tile.zCoord);
            }
        }
    }

    public void chunkUnWatch(EntityPlayerMP player, Chunk chunk) {
        for (TileEntity tile : chunk.chunkTileEntityMap.values()) {
            if (tile instanceof TileEntityLittleChunk) {
                blockUnWatch(player, tile.xCoord, tile.zCoord);
            }
        }
    }

    static long getMultipartChunkKey(int blockX, int blockZ) {
        ChunkCoordIntPair chunkCoords = getMultipartChunkCoords(blockX, blockZ);
        return ChunkCoordIntPair.chunkXZ2Int(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
    }

    private static ChunkCoordIntPair getMultipartChunkCoords(int blockX, int blockZ) {
        return new ChunkCoordIntPair(blockX >> 1, blockZ >> 1);
    }
}
