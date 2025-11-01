package net.slimevoid.littleblocks.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.slimevoid.littleblocks.world.LittleWorldClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFX.class)
public abstract class MixinEntityFX extends Entity {

    public MixinEntityFX() {
        super(null);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDD)V", at = @At("TAIL"))
    private void init(World world, double x, double y, double z, CallbackInfo ci) {
        if (world instanceof LittleWorldClient) {
            world = Minecraft.getMinecraft().theWorld;

            this.setPosition(y / 8, y / 8, z / 8);
            this.lastTickPosX = x / 8;
            this.lastTickPosY = y / 8;
            this.lastTickPosZ = z / 8;
            this.worldObj = world;
            this.dimension = world.provider.dimensionId;
        }
    }
}
