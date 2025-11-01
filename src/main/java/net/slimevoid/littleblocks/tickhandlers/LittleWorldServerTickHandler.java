package net.slimevoid.littleblocks.tickhandlers;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.slimevoid.littleblocks.core.LittleBlocks;
import net.slimevoid.littleblocks.core.lib.CoreLib;
import net.slimevoid.littleblocks.tileentities.TileEntityLittleChunk;

import com.mojang.authlib.GameProfile;

import codechicken.multipart.handler.MultipartSPH;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import scala.collection.JavaConverters;
import scala.collection.Seq;

public class LittleWorldServerTickHandler {

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Seq<EntityPlayerMP> players = JavaConverters.asScalaIteratorConverter(
                fakePlayerMap.values()
                    .iterator())
                .asScala()
                .toSeq();
            MultipartSPH.onTickEnd(players);
        }
    }

    private static HashMap<World, EntityPlayerMP> fakePlayerMap = new HashMap<>();

    private static EntityPlayerMP getFakePlayer(EntityPlayerMP player) {
        return fakePlayerMap.computeIfAbsent(player.worldObj, ignored -> {
            WorldServer littleworld = (WorldServer) LittleBlocks.proxy.getLittleWorld(player.worldObj, false);
            EntityPlayerMP fakePlayer = new FakePlayer(littleworld, new GameProfile(null, CoreLib.MOD_CHANNEL));
            fakePlayer.playerNetServerHandler = new NetHandlerPlayServer(
                player.mcServer,
                player.playerNetServerHandler.netManager,
                player); // TODO!!!
            return fakePlayer;
        });
    }

    @SubscribeEvent
    public void chunkWatch(ChunkWatchEvent.Watch watch) {
        EntityPlayerMP fakePlayer = getFakePlayer(watch.player);
        for (int x = 0; x < 8; x++) {
            for (int z = 0; z < 8; z++) {
                ChunkCoordIntPair pos = new ChunkCoordIntPair(
                    (watch.chunk.chunkXPos << 3) + x,
                    (watch.chunk.chunkZPos << 3) + z);
                MultipartSPH.onChunkWatch(fakePlayer, pos);
            }
        }
    }

    @SubscribeEvent
    public void chunkUnWatch(ChunkWatchEvent.UnWatch watch) {
        if (watch.player instanceof FakePlayer) {
            return;
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        for (TileEntity tile : event.getChunk().chunkTileEntityMap.values()) {
            if (tile instanceof TileEntityLittleChunk tileChunk) {
                tileChunk.onChunkLoad();
            }
        }
    }

}
