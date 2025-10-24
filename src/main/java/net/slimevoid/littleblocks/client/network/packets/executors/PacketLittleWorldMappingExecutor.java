package net.slimevoid.littleblocks.client.network.packets.executors;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.slimevoid.library.IPacketExecutor;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.littleblocks.network.packets.PacketLittleWorldMapping;
import net.slimevoid.littleblocks.world.LittleWorldMapping;

public class PacketLittleWorldMappingExecutor implements IPacketExecutor {

    @Override
    public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
        if (packet instanceof PacketLittleWorldMapping) {
            PacketLittleWorldMapping packetMapping = (PacketLittleWorldMapping) packet;
            LittleWorldMapping.clientAddMapping(packetMapping.originalId, packetMapping.littleId);
        }
    }

}
