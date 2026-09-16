package net.slimevoid.littleblocks.world;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.littleblocks.api.ILittleWorld;
import net.slimevoid.littleblocks.core.lib.ConfigurationLib;
import net.slimevoid.littleblocks.network.packets.PacketLittleWorldMapping;

import com.gtnewhorizon.gtnhlib.api.world.WorldContextRegistry;

public class LittleWorldMapping {

    /** Namespace separating LittleBlocks IDs from other virtual-world implementations. */
    private static final String WORLD_CONTEXT_NAMESPACE = "littleblocks";

    /** A parent world owns exactly one LittleWorld. */
    private static final int LITTLE_SUB_ID = 1;

    private static final HashMap<Integer, Integer> dimensionMappingsClient = new HashMap<>();
    private static final HashMap<Integer, Integer> dimensionMappingsServer = new HashMap<>();

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

            @Override
            public World getSubWorld(World hostWorld, int subId) {
                if (subId != LITTLE_SUB_ID) return null;
                if (hostWorld.isRemote) {
                    World littleWorld = ConfigurationLib.littleWorldClient;
                    return littleWorld != null && ((ILittleWorld) littleWorld).getParentWorld() == hostWorld
                        ? littleWorld
                        : null;
                }
                Integer littleDimension = ConfigurationLib.littleWorldServer.get(hostWorld.provider.dimensionId);
                return littleDimension == null ? null : DimensionManager.getWorld(littleDimension);
            }
        });
    }
}
