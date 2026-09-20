package net.slimevoid.littleblocks.world;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.littleblocks.api.ILittleWorld;
import net.slimevoid.littleblocks.core.lib.ConfigurationLib;
import net.slimevoid.littleblocks.network.packets.PacketLittleWorldMapping;
import net.slimevoid.littleblocks.tileentities.TileEntityLittleChunk;

import com.gtnewhorizon.gtnhlib.api.world.WorldContextRegistry;

public class LittleWorldMapping {

    /** Namespace separating LittleBlocks IDs from other virtual-world implementations. */
    private static final String WORLD_CONTEXT_NAMESPACE = "littleblocks";

    /** A parent world owns exactly one LittleWorld. */
    private static final int LITTLE_SUB_ID = 1;

    private static final HashMap<Integer, Integer> dimensionMappingsClient = new HashMap<>();
    private static final HashMap<Integer, Integer> dimensionMappingsServer = new HashMap<>();

    /**
     * Parent dimension to the LittleWorld instance constructed for it. A LittleWorld is deliberately not placed in
     * {@code DimensionManager}, which would enter it into {@code MinecraftServer.worldServers} and tick it as an
     * ordinary dimension; it is still the world subworld lookups must resolve to, so the instance is held here.
     */
    private static final HashMap<Integer, WorldServer> littleWorldServers = new HashMap<>();

    public static void serverAddWorld(int originalId, WorldServer littleWorld) {
        littleWorldServers.put(originalId, littleWorld);
    }

    public static WorldServer serverRemoveWorld(int originalId) {
        return littleWorldServers.remove(originalId);
    }

    /** @return the server LittleWorld of a parent world, or {@code null} if it has none. */
    public static WorldServer getServerLittleWorld(World parentWorld) {
        return parentWorld == null ? null : littleWorldServers.get(parentWorld.provider.dimensionId);
    }

    public static int clientGetMapping(int originalId) {
        return dimensionMappingsClient.getOrDefault(originalId, 0);
    }

    public static void clientAddMapping(int originalId, int littleId) {
        dimensionMappingsClient.put(originalId, littleId);
        World worldReal = ConfigurationLib.littleWorldClient.getParentWorld();
        World worldLittle = ConfigurationLib.littleWorldClient.getLittleWorld();
        if (worldReal.provider.dimensionId == originalId) {
            worldLittle.provider.dimensionId = littleId;
        }
    }

    public static void serverAddMapping(int originalId, int littleId) {
        dimensionMappingsServer.put(originalId, littleId);
        PacketLittleWorldMapping packet = new PacketLittleWorldMapping();
        packet.setIds(originalId, littleId);
        PacketHelper.broadcastPacket(packet);
    }

    public static void serverPlayerJoin(EntityPlayerMP player) {
        for (int originalId : dimensionMappingsServer.keySet()) {
            PacketLittleWorldMapping packet = new PacketLittleWorldMapping();
            packet.setIds(originalId, dimensionMappingsServer.get(originalId));
            PacketHelper.sendToPlayer(packet, player);
        }
    }

    public static void registerHandlers() {
        WorldContextRegistry.registerHandler(WORLD_CONTEXT_NAMESPACE, new WorldContextRegistry.Handler() {

            @Override
            public int getSubId(World world) {
                return world instanceof ILittleWorld ? LITTLE_SUB_ID : WorldContextRegistry.UNKNOWN_SUB_ID;
            }

            @Override
            public World getHostWorld(World world) {
                return world instanceof ILittleWorld ? ((ILittleWorld) world).getParentWorld() : null;
            }

            @Override
            public Collection<World> getSubWorlds(World hostWorld) {
                World littleWorld = getSubWorld(hostWorld, LITTLE_SUB_ID);
                return littleWorld == null ? Collections.<World>emptyList()
                    : Collections.singletonList(littleWorld);
            }

            /**
             * A LittleWorld chunk is visible exactly while the parent chunk holding its containers is watched, so it
             * names that chunk rather than being watched in its own right. Eight little blocks span one parent block,
             * so a LittleWorld chunk spans two parent blocks and a parent chunk covers eight by eight of them.
             */
            @Override
            public ChunkCoordIntPair getHostChunk(World subWorld, ChunkCoordIntPair subChunk) {
                if (!(subWorld instanceof ILittleWorld)) return null;
                return new ChunkCoordIntPair(subChunk.chunkXPos >> 3, subChunk.chunkZPos >> 3);
            }

            /** Only chunks actually backed by a loaded container are visible; the rest of the range is empty. */
            @Override
            public Map<World, Collection<ChunkCoordIntPair>> getVisibleChunks(World hostWorld,
                ChunkCoordIntPair hostChunk) {
                World littleWorld = getSubWorld(hostWorld, LITTLE_SUB_ID);
                if (littleWorld == null) return Collections.emptyMap();

                Chunk parentChunk = hostWorld.getChunkFromChunkCoords(hostChunk.chunkXPos, hostChunk.chunkZPos);
                Set<ChunkCoordIntPair> chunks = new LinkedHashSet<>();
                for (Object tile : parentChunk.chunkTileEntityMap.values()) {
                    if (tile instanceof TileEntityLittleChunk) {
                        TileEntity container = (TileEntity) tile;
                        chunks.add(new ChunkCoordIntPair(container.xCoord >> 1, container.zCoord >> 1));
                    }
                }
                return chunks.isEmpty() ? Collections.<World, Collection<ChunkCoordIntPair>>emptyMap()
                    : Collections.<World, Collection<ChunkCoordIntPair>>singletonMap(littleWorld, chunks);
            }

            @Override
            public World getSubWorld(World hostWorld, int subId) {
                if (subId != LITTLE_SUB_ID) return null;
                if (hostWorld.isRemote) {
                    World littleWorld = ConfigurationLib.littleWorldClient;
                    return littleWorld != null && ((ILittleWorld) littleWorld).getParentWorld() == hostWorld
                        ? littleWorld
                        : null;
                }
                return getServerLittleWorld(hostWorld);
            }
        });
    }
}
