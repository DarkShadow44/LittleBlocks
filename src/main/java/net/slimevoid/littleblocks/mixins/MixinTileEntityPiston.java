package net.slimevoid.littleblocks.mixins;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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

    @Inject(method = "updateEntity", at = @At("HEAD"))
    public void updateEntity(CallbackInfo ci) {
        if (this.progress >= 1.0F) {
            if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) == Blocks.piston_extension) {
                this.worldObj
                    .setBlock(this.xCoord, this.yCoord, this.zCoord, this.storedBlock, this.storedMetadata, 11);
                this.worldObj.notifyBlockOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.storedBlock);
            }
        }
    }
}
