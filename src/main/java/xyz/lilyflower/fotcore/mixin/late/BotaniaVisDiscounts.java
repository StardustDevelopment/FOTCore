package xyz.lilyflower.fotcore.mixin.late;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import vazkii.botania.common.item.equipment.armor.manaweave.ItemManaweaveArmor;

public class BotaniaVisDiscounts {
    @Mixin(ItemManaweaveArmor.class)
    public static abstract class Manaweave extends ItemArmor implements IVisDiscountGear {
        public Manaweave(ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_) {
            super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
        }

        @Override
        public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
            return 2;
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, null) + "%");
        }
    }
}
