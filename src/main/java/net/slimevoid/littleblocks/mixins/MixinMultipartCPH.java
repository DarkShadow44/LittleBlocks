package net.slimevoid.littleblocks.mixins;

import net.minecraft.world.World;
import net.slimevoid.littleblocks.core.LittleBlocks;
import net.slimevoid.mixin_interfaces.IPacketCustom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import codechicken.lib.packet.PacketCustom;
import codechicken.multipart.handler.MultipartCPH$;

@Mixin(MultipartCPH$.class)
public class MixinMultipartCPH {

    @WrapOperation(
        method = "handlePacket",
        at = @At(
            value = "INVOKE",
            target = "Lcodechicken/multipart/handler/MultipartCPH$;handleCompressedTileData(Lcodechicken/lib/packet/PacketCustom;Lnet/minecraft/world/World;)V"),
        remap = false)
    private void handleTileData(MultipartCPH$ instance, PacketCustom packetCustom, World world,
        Operation<Void> original) {
        IPacketCustom packet = (IPacketCustom) (Object) packetCustom;
        if (packet.littleblocks$getHasLittleWorld()) {
            world = (World) LittleBlocks.proxy.getLittleWorld(world, false);
        }
        original.call(instance, packetCustom, world);
    }

    @WrapOperation(
        method = "handlePacket",
        at = @At(
            value = "INVOKE",
            target = "Lcodechicken/multipart/handler/MultipartCPH$;handleCompressedTileDesc(Lcodechicken/lib/packet/PacketCustom;Lnet/minecraft/world/World;)V"),
        remap = false)
    private void handleTileDesc(MultipartCPH$ instance, PacketCustom packetCustom, World world,
        Operation<Void> original) {
        IPacketCustom packet = (IPacketCustom) (Object) packetCustom;
        if (packet.littleblocks$getHasLittleWorld()) {
            world = (World) LittleBlocks.proxy.getLittleWorld(world, false);
        }
        original.call(instance, packetCustom, world);
    }
}
