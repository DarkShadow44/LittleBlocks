package net.slimevoid.littleblocks.mixins;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.slimevoid.littleblocks.api.ILittleWorld;
import net.slimevoid.littleblocks.core.LittleBlocks;
import net.slimevoid.mixin_interfaces.IGuiData;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cleanroommc.modularui.api.UIFactory;
import com.cleanroommc.modularui.factory.GuiData;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
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

    @WrapOperation(
        method = "open",
        at = @At(
            value = "INVOKE",
            target = "Lcom/cleanroommc/modularui/factory/GuiManager;open(Lcom/cleanroommc/modularui/api/UIFactory;Lcom/cleanroommc/modularui/factory/GuiData;Lnet/minecraft/entity/player/EntityPlayerMP;)V"),
        remap = false)
    private static <T extends GuiData> void open2(@NotNull UIFactory<T> factory, @NotNull T guiData,
        EntityPlayerMP player, Operation<Void> original, @Local(argsOnly = true) IMetaTileEntity mte) {
        World mteWorld = mte.getBaseMetaTileEntity()
            .getWorld();
        if (mteWorld instanceof ILittleWorld) {
            ((IGuiData) guiData).littleblocks$setWorld(mteWorld);
        }

        original.call(factory, guiData, player);
    }

    @Inject(
        method = "writeGuiData(Lcom/cleanroommc/modularui/factory/PosGuiData;Lnet/minecraft/network/PacketBuffer;)V",
        at = @At("TAIL"),
        remap = false)
    private void writeGuIData(PosGuiData guiData, PacketBuffer buffer, CallbackInfo ci) {
        buffer.writeBoolean(((IGuiData) guiData).littleblocks$hasLittleWorld());
    }

    @WrapMethod(
        method = "readGuiData(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/network/PacketBuffer;)Lcom/cleanroommc/modularui/factory/PosGuiData;",
        remap = false)
    private PosGuiData readGuiData(EntityPlayer player, PacketBuffer buffer, Operation<PosGuiData> original) {
        PosGuiData data = original.call(player, buffer);
        if (buffer.readBoolean()) {
            ((IGuiData) data).littleblocks$setWorld((World) LittleBlocks.proxy.getLittleWorld(data.getWorld(), false));
        }
        return data;
    }

}
