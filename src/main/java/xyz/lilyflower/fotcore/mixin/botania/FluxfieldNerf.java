package xyz.lilyflower.fotcore.mixin.botania;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vazkii.botania.common.block.tile.mana.TileRFGenerator;

@Mixin(TileRFGenerator.class)
public class FluxfieldNerf {
    @ModifyArg(method = "updateEntity", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), index = 1)
    public int nerf(int rate) {
        return 40;
    }
}
