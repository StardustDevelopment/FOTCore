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
import vazkii.botania.common.item.equipment.armor.terrasteel.ItemTerrasteelArmor;
import vazkii.botania.common.item.interaction.thaumcraft.ItemTerrasteelHelmRevealing;

public class BotaniaVisDiscounts {
    @Mixin(ItemManaweaveArmor.class)
    public static abstract class Manaweave extends ItemArmor implements IVisDiscountGear {
        public Manaweave(ArmorMaterial material, int index, int type) {
            super(material, index, type);
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

    @Mixin(ItemTerrasteelArmor.class)
    public static abstract class Terrasteel extends ItemArmor implements IVisDiscountGear {
        public Terrasteel(ArmorMaterial material, int index, int type) {
            super(material, index, type);
        }

        @Override
        public int getVisDiscount(ItemStack itemStack, EntityPlayer entityPlayer, Aspect aspect) {
            if ((ItemTerrasteelArmor) (Object) this instanceof ItemTerrasteelHelmRevealing) {
                return 5;
            }

            return switch (this.armorType) {
                case 0 -> 3;
                case 1, 2 -> 2;
                case 3 -> 1;
                default -> 0;
            };
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
            list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + this.getVisDiscount(stack, player, null) + "%");
        }
    }
}
