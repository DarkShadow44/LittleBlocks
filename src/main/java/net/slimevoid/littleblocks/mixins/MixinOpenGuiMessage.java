package net.slimevoid.littleblocks.mixins;

import net.slimevoid.mixin_interfaces.IOpenGui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.network.internal.FMLMessage;
import io.netty.buffer.ByteBuf;

@Mixin(FMLMessage.OpenGui.class)
public class MixinOpenGuiMessage implements IOpenGui {

    @Unique
    private boolean littleBlocks$isLittleWorld;

    @Unique
    public void littleBlocks$setLittleWorld(boolean value) {
        littleBlocks$isLittleWorld = value;
    }

    @Unique
    public boolean littleBlocks$isLittleWorld() {
        return littleBlocks$isLittleWorld;
    }

    @Inject(method = "fromBytes", at = @At("TAIL"), remap = false)
    private void fromBytes(ByteBuf buf, CallbackInfo ci) {
        littleBlocks$isLittleWorld = buf.readBoolean();
    }

    @Inject(method = "toBytes", at = @At("TAIL"), remap = false)
    private void toBytes(ByteBuf buf, CallbackInfo ci) {
        buf.writeBoolean(littleBlocks$isLittleWorld);
    }
}
