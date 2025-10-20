package net.slimevoid.littleblocks;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

public enum Mixins implements IMixins {

    CORE(new MixinBuilder().setPhase(Phase.EARLY)
        .addCommonMixins(
            "MixinTileEntityPiston",
            "MixinOpenGuiMessage",
            "MixinFMLNetworkHandler",
            "MixinOpenGuiHandler")),

    GT5(new MixinBuilder().setPhase(Phase.LATE)
        .addRequiredMod(TargetedMod.GT5)
        .addCommonMixins("MixinBaseMetaTileEntity"));

    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Override
    public MixinBuilder getBuilder() {
        return builder;
    }
}
