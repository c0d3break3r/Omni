package pugz.omni.common.block.forestry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import pugz.omni.core.util.IBaseBlock;

public class CarvedLogBlock extends Block implements IBaseBlock {
    public CarvedLogBlock(MaterialColor color) {
        super(AbstractBlock.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    @Override
    public int getFireEncouragement() {
        return 5;
    }

    @Override
    public int getFireFlammability() {
        return 5;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        items.forEach(stack -> {
            if (stack.getItem() == Items.DARK_OAK_WOOD) items.add(new ItemStack(this));
        });
    }
}