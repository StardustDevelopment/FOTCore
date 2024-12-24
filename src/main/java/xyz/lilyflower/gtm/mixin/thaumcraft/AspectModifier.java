package xyz.lilyflower.gtm.mixin.thaumcraft;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.research.ScanManager;
import xyz.lilyflower.gtm.GTM;

@Mixin(ScanManager.class)
public class AspectModifier {
    @Inject(method = "generateEntityAspects", at = @At("HEAD"), cancellable = true, remap = false)
    private static void modifyPlayerAspects(Entity entity, CallbackInfoReturnable<AspectList> cir) {
        if (entity instanceof EntityPlayer player) {
            if (GTM.Config.Thaumcraft.PLAYER_ASPECTS.containsKey(player.getCommandSenderName())) {
                cir.setReturnValue(GTM.Config.Thaumcraft.PLAYER_ASPECTS.get(player.getCommandSenderName()));
            }

            switch (GTM.Config.Thaumcraft.ASPECT_GENERATION_MODE) {
                case RANDOM -> {
                    // NOP - behave as usual
                }

                case STATIC -> cir.setReturnValue(GTM.Config.Thaumcraft.STATIC_ASPECTS);

                case NONHUMAN -> {
                    AspectList list = new AspectList();
                    String name = "player_" + player.getCommandSenderName();

                    Random rand = new Random(name.hashCode());
                    Aspect[] posa = Aspect.aspects.values().toArray(new Aspect[0]);

                    list.add(GTM.Config.Thaumcraft.NON_HUMAN_ASPECT, 4);
                    list.add(posa[rand.nextInt(posa.length)], 4);
                    list.add(posa[rand.nextInt(posa.length)], 4);
                    list.add(posa[rand.nextInt(posa.length)], 4);
                }

                case NONE -> cir.setReturnValue(new AspectList());
            }
        }
    }
}
