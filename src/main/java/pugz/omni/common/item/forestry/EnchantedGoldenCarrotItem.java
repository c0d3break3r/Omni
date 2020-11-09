package pugz.omni.common.item.forestry;

import net.minecraft.item.*;
import pugz.omni.core.util.OmniFoods;

public class EnchantedGoldenCarrotItem extends Item {
    public EnchantedGoldenCarrotItem() {
        super((new Item.Properties()).group(ItemGroup.FOOD).rarity(Rarity.EPIC).food(OmniFoods.ENCHANTED_GOLDEN_CARROT));
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
