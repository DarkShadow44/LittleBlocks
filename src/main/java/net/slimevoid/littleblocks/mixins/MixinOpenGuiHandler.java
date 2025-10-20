package net.slimevoid.littleblocks.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.slimevoid.littleblocks.core.LittleBlocks;
import net.slimevoid.mixin_interfaces.IOpenGui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.internal.OpenGuiHandler;

@Mixin(OpenGuiHandler.class)
public class MixinOpenGuiHandler {

    @WrapOperation(
        method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lcpw/mods/fml/common/network/internal/FMLMessage$OpenGui;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/EntityPlayer;openGui(Ljava/lang/Object;ILnet/minecraft/world/World;III)V"),
        remap = false)
    private void openGui(EntityPlayer instance, Object mod, int modGuiId, World world, int x, int y, int z,
        Operation<Void> original, @Local(argsOnly = true) FMLMessage.OpenGui msg) {
        if (((IOpenGui) msg).littleBlocks$isLittleWorld()) {
            world = (World) LittleBlocks.proxy.getLittleWorld(world, false);
        }
        instance.openGui(mod, modGuiId, world, x, y, z);
    }

}
