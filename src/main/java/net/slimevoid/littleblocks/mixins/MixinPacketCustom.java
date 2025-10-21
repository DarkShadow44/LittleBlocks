package net.slimevoid.littleblocks.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.slimevoid.littleblocks.world.LittleWorldServer;
import net.slimevoid.mixin_interfaces.IPacketCustom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import codechicken.lib.packet.PacketCustom;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Mixin(PacketCustom.class)
public abstract class MixinPacketCustom implements IPacketCustom {

    @Unique
    private boolean littleblocks$hasLittleWorld;

    @Shadow(remap = false)
    public abstract FMLProxyPacket toPacket();

    @WrapOperation(
        method = "toPacket",
        at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;copy()Lio/netty/buffer/ByteBuf;"),
        remap = false)
    private ByteBuf write(ByteBuf instance, Operation<ByteBuf> original) {
        ByteBuf copy = Unpooled.buffer();
        copy.writeBoolean(littleblocks$hasLittleWorld);
        copy.writeBytes(instance);
        return copy;
    }

    @Inject(method = "sendToPlayer(Lnet/minecraft/entity/player/EntityPlayer;)V", at = @At("HEAD"), remap = false)
    private void sendToPlayer(EntityPlayer player, CallbackInfo ci) {
        littleblocks$hasLittleWorld = player.worldObj instanceof LittleWorldServer;
    }

    @WrapOperation(
        method = "<init>(Lio/netty/buffer/ByteBuf;)V",
        at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;readUnsignedByte()S"),
        remap = false)
    private short read(ByteBuf instance, Operation<Short> original) {
        littleblocks$hasLittleWorld = instance.readBoolean();
        return instance.readUnsignedByte();
    }

    @Override
    public boolean littleblocks$getHasLittleWorld() {
        return littleblocks$hasLittleWorld;
    }
}
