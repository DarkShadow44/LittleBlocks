package net.slimevoid.littleblocks.mixins;

import net.minecraft.client.particle.EntitySmokeFX;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntitySmokeFX.class)
public interface MixinEntitySmokeFX {

    @Accessor
    void setSmokeParticleScale(float value);

    @Accessor
    float getSmokeParticleScale();
}
