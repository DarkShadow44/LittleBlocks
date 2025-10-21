package net.slimevoid.littleblocks.mixins;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.RenderGlobal;
import net.slimevoid.littleblocks.core.lib.ConfigurationLib;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @WrapOperation(
        method = "spawnParticle",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderGlobal;doSpawnParticle(Ljava/lang/String;DDDDDD)Lnet/minecraft/client/particle/EntityFX;"))
    private EntityFX spawnParticle(RenderGlobal instance, String name, double x, double y, double z, double velX,
        double velY, double velZ, Operation<EntityFX> original) {
        String prefix = "littleblocks$";
        boolean isLittleBlocks = false;
        if (name.startsWith(prefix)) {
            isLittleBlocks = true;
            name = name.substring(prefix.length());
        }
        EntityFX fx = original.call(instance, name, x, y, z, velX, velY, velZ);
        if (isLittleBlocks) {
            float scale = 1.0f / ConfigurationLib.littleBlocksSize;
            fx.multipleParticleScaleBy(scale);
            fx.multiplyVelocity(scale);
            fx.setPosition(fx.posX, fx.posY, fx.posZ);
            if (fx instanceof EntitySmokeFX) {
                MixinEntitySmokeFX fx2 = (MixinEntitySmokeFX) fx;
                fx2.setSmokeParticleScale(fx2.getSmokeParticleScale() * scale);
            }
        }
        return fx;
    }
}
