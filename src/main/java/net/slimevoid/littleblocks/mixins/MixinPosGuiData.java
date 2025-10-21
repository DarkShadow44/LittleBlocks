package net.slimevoid.littleblocks.mixins;

import net.slimevoid.mixin_interfaces.IGuiData;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.cleanroommc.modularui.factory.PosGuiData;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(PosGuiData.class)
public abstract class MixinPosGuiData implements IGuiData {

    @Shadow(remap = false)
    @Final
    private int x, y, z;

    @WrapMethod(method = "getSquaredDistance(DDD)D", remap = false)
    private double getSquaredDistance(double x, double y, double z, Operation<Double> original) {
        if (littleblocks$hasLittleWorld()) {
            double dx = this.x / 8 + 0.5 - x;
            double dy = this.y / 8 + 0.5 - y;
            double dz = this.z / 8 + 0.5 - z;
            return dx * dx + dy * dy + dz * dz;
        }
        return original.call(x, y, z);
    }
}
