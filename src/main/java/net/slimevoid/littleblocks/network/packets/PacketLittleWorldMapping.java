package net.slimevoid.littleblocks.network.packets;

import net.slimevoid.library.network.PacketIds;
import net.slimevoid.library.network.PacketUpdate;
import net.slimevoid.littleblocks.core.lib.CommandLib;
import net.slimevoid.littleblocks.core.lib.CoreLib;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketLittleWorldMapping extends PacketUpdate {

    public int originalId;
    public int littleId;

    public PacketLittleWorldMapping() {
        super(PacketIds.MAPPING);
        this.setChannel(CoreLib.MOD_CHANNEL);
        this.command = CommandLib.MAPPING;
    }

    public void setIds(int originalId, int littleId) {
        this.originalId = originalId;
        this.littleId = littleId;
    }

    @Override
    public void writeData(ChannelHandlerContext ctx, ByteBuf data) {
        data.writeByte(this.getPacketId());

        data.writeInt(this.originalId);
        data.writeInt(this.littleId);
    }

    @Override
    public void readData(ChannelHandlerContext ctx, ByteBuf data) {
        originalId = data.readInt();
        littleId = data.readInt();
    }
}
