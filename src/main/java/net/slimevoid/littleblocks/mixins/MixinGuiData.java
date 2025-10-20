package net.slimevoid.littleblocks.mixins;

import net.minecraft.world.World;
import net.slimevoid.mixin_interfaces.IGuiData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.cleanroommc.modularui.factory.GuiData;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

@Mixin(GuiData.class)
public class MixinGuiData implements IGuiData {

    @Unique
    World littleblocks$world;

    public void littleblocks$setWorld(World world) {
        littleblocks$world = world;
    }

    @WrapMethod(method = "getWorld", remap = false)
    public World getWorld(Operation<World> original) {
        if (littleblocks$world != null) {
            return littleblocks$world;
        }
        return original.call();
    }

}
