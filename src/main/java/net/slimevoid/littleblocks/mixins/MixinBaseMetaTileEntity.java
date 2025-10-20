package net.slimevoid.littleblocks.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.slimevoid.littleblocks.world.LittleWorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import gregtech.api.metatileentity.BaseMetaTileEntity;

@Mixin(BaseMetaTileEntity.class)
public class MixinBaseMetaTileEntity extends TileEntity {

    @WrapOperation(
        method = "isUseableByPlayer",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getDistanceSq(DDD)D"))
    private double getDistanceSq(EntityPlayer instance, double x, double y, double z, Operation<Double> original) {
        if (worldObj instanceof LittleWorldServer) {
            x /= 8;
            y /= 8;
            z /= 8;
        }
        return instance.getDistanceSq(x, y, z);
    }
}
