package net.slimevoid.littleblocks.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.slimevoid.littleblocks.core.lib.CoreLib;

import com.mojang.authlib.GameProfile;

public class LittleFakeEntityPlayer {

    private static void copyValues(EntityPlayer target, EntityPlayer source) {
        target.posX = source.posX * 8;
        target.posY = source.posY * 8;
        target.posZ = source.posZ * 8;
        target.rotationYaw = source.rotationYaw;
        target.prevRotationYaw = source.rotationYaw;
        target.rotationPitch = source.rotationPitch;
        target.prevRotationPitch = source.prevRotationPitch;
    }

    public static EntityPlayer makeForClient(World world, EntityPlayer player) {
        EntityPlayer fake = new EntityPlayer(world, new GameProfile(null, CoreLib.MOD_CHANNEL)) {

            @Override
            public void addChatMessage(IChatComponent message) {

            }

            @Override
            public boolean canCommandSenderUseCommand(int permissionLevel, String command) {
                return false;
            }

            @Override
            public ChunkCoordinates getPlayerCoordinates() {
                return null;
            }

            @Override
            public float getEyeHeight() {
                return player == null ? 0 : player.getEyeHeight() * 8;
            }

            @Override
            public float getDefaultEyeHeight() {
                return player == null ? 0 : player.getDefaultEyeHeight() * 8;
            }
        };
        copyValues(fake, player);
        return fake;
    }

    public static EntityPlayer makeForServer(WorldServer world, EntityPlayerMP player) {
        FakePlayer fake = new FakePlayer(world, new GameProfile(null, CoreLib.MOD_CHANNEL)) {

            @Override
            public float getEyeHeight() {
                return player == null ? 0 : player.getEyeHeight() * 8;
            }

            @Override
            public float getDefaultEyeHeight() {
                return player == null ? 0 : player.getDefaultEyeHeight() * 8;
            }
        };
        copyValues(fake, player);
        fake.theItemInWorldManager.setBlockReachDistance(player.theItemInWorldManager.getBlockReachDistance() * 8);
        return fake;
    }
}
