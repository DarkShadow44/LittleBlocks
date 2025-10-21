package net.slimevoid.littleblocks.mixins;

import net.minecraft.client.renderer.Tessellator;
import net.slimevoid.mixin_interfaces.ITessellator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(Tessellator.class)
public class MixinTessellator implements ITessellator {

    @Unique
    private float littleblocks$scale = 1;

    @Unique
    @Override
    public void littleblocks$setScale(float scale) {
        littleblocks$scale = scale;
    }

    @WrapMethod(method = "addVertex")
    private void addVertex(double x, double y, double z, Operation<Void> original) {
        original.call(x * littleblocks$scale, y * littleblocks$scale, z * littleblocks$scale);
    }
}
