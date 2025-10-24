package net.slimevoid.littleblocks.world;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.slimevoid.library.util.helpers.PacketHelper;
import net.slimevoid.littleblocks.core.lib.ConfigurationLib;
import net.slimevoid.littleblocks.network.packets.PacketLittleWorldMapping;

import codechicken.ClientWorldMappingRegistry;

public class LittleWorldMapping {

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
        ClientWorldMappingRegistry.registerHandler(id -> {
            if (ConfigurationLib.littleWorldClient.provider.dimensionId == id) {
                return ConfigurationLib.littleWorldClient;
            }
            return null;
        });
    }
}
