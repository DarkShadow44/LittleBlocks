package net.slimevoid.littleblocks.mixins;

import net.minecraft.world.World;
import net.slimevoid.littleblocks.world.LittleWorldServer;
import net.slimevoid.mixin_interfaces.IOpenGui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import io.netty.channel.embedded.EmbeddedChannel;

@Mixin(FMLNetworkHandler.class)
public class MixinFMLNetworkHandler {

    @WrapOperation(
        method = "openGui",
        at = @At(
            value = "INVOKE",
            target = "Lio/netty/channel/embedded/EmbeddedChannel;writeOutbound([Ljava/lang/Object;)Z"),
        remap = false)
    private static boolean openGui(EmbeddedChannel instance, Object[] arr, Operation<Boolean> original,
        @Local(argsOnly = true) World world) {
        IOpenGui gui = (IOpenGui) arr[0];
        gui.littleBlocks$setLittleWorld(world instanceof LittleWorldServer);
        return original.call(instance, arr);
    }
}
