package net.slimevoid.littleblocks.mixins;

import net.minecraft.world.World;
import net.slimevoid.littleblocks.api.ILittleWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.modularui2.MetaTileEntityGuiHandler;

@Mixin(MetaTileEntityGuiHandler.class)
public class MixinMetaTileEntityGuiHandler {

    @WrapOperation(
        method = "open",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/interfaces/tileentity/IGregTechTileEntity;getWorld()Lnet/minecraft/world/World;"),
        remap = false)
    private static World open(IGregTechTileEntity instance, Operation<World> original) {
        World world = original.call(instance);
        if (world instanceof ILittleWorld littleWorld) {
            world = littleWorld.getParentWorld();
        }
        return world;
    }
}
