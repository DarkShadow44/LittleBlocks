package net.slimevoid.littleblocks.mixins;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityPiston.class)
public class MixinTileEntityPiston extends TileEntity {

    @Shadow
    private boolean shouldHeadBeRendered;
    @Shadow
    private float progress;
    @Shadow
    private Block storedBlock;

    @Shadow
    private int storedMetadata;

    @Inject(method = "writeToNBT", at = @At("TAIL"))
    private void writeToNBT(NBTTagCompound compound, CallbackInfo ci) {
        compound.setBoolean("shouldHeadBeRendered", shouldHeadBeRendered);
    }

    @Inject(method = "readFromNBT", at = @At("TAIL"))
    private void readFromNBT(NBTTagCompound compound, CallbackInfo ci) {
        if (compound.hasKey("shouldHeadBeRendered")) {
            shouldHeadBeRendered = compound.getBoolean("shouldHeadBeRendered");
        }
    }
}
